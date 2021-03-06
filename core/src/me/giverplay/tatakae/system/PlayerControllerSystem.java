package me.giverplay.tatakae.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import me.giverplay.tatakae.entity.component.CollidableComponent;
import me.giverplay.tatakae.entity.component.RigidBodyComponent;
import me.giverplay.tatakae.entity.component.JumpComponent;
import me.giverplay.tatakae.entity.component.PlayerComponent;

import static com.badlogic.gdx.Input.Keys.*;

public class PlayerControllerSystem extends IteratingSystem
{
  private ComponentMapper<me.giverplay.tatakae.entity.component.CollidableComponent> collidableComponentMapper;
  private ComponentMapper<me.giverplay.tatakae.entity.component.RigidBodyComponent> rigidBodyComponentMapper;
  private ComponentMapper<PlayerComponent> playerComponentMapper;
  private ComponentMapper<JumpComponent> jumpComponentMapper;

  private boolean movingRight;
  private boolean movingLeft;
  private boolean jumping;

  public PlayerControllerSystem()
  {
    super(Aspect.all(PlayerComponent.class, me.giverplay.tatakae.entity.component.RigidBodyComponent.class, JumpComponent.class, me.giverplay.tatakae.entity.component.CollidableComponent.class));
    Gdx.input.setInputProcessor(new InputMultiplexer(new GameInputAdapter()));
  }

  @Override
  protected void process(int entityId)
  {
    CollidableComponent collidable = collidableComponentMapper.get(entityId);
    RigidBodyComponent rigidBody = rigidBodyComponentMapper.get(entityId);
    PlayerComponent player = playerComponentMapper.get(entityId);
    JumpComponent jump = jumpComponentMapper.get(entityId);

    if(player.canWalk)
    {
      if(movingRight == movingLeft)
      {
        rigidBody.velocity.x = 0;
      }
      else if(movingRight)
      {
        rigidBody.velocity.x = player.walkSpeed;
      }
      else {
        rigidBody.velocity.x = -player.walkSpeed;
      }
    }

    if(jumping && jump.canJump && collidable.onGround)
    {
      rigidBody.velocity.y = jump.jumpSpeed;
    }
  }

  private class GameInputAdapter extends InputAdapter
  {
    @Override
    public boolean keyDown(int keycode)
    {
      toggle(keycode, true);
      return true;
    }

    @Override
    public boolean keyUp(int keycode)
    {
      toggle(keycode, false);
      return true;
    }

    private void toggle(int keycode, boolean press)
    {
      switch(keycode) {
        case D:
        case RIGHT:
          movingRight = press;
          break;

        case A:
        case LEFT:
          movingLeft = press;
          break;

        case SPACE:
          jumping = press;
          break;
      }
    }
  }
}
