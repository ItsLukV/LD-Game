package dk.mtdm.managementSystem.Entitys;

import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import dk.mtdm.exceptions.MissingBlockTypeException;
import dk.mtdm.exceptions.MissingDataException;
import dk.mtdm.itemsAndMore.Blocks.Block;
import dk.mtdm.location.LDVector;
import dk.mtdm.location.LocationTypes;
import dk.mtdm.location.WWL;
import dk.mtdm.location.WorldWideLocation;
import dk.mtdm.managementSystem.world.World;
import processing.core.PGraphics;

public abstract class Entity {
  protected static WWL pos;
  public static int width;
  public static int height;
  protected LDVector speed = new LDVector(0, 0);
  protected int moveSpeed = 6;
  protected float airRes = 0.8f;
  public static boolean noClip = false;
  protected int gravityAcc = 2;
  protected boolean gravity = true;
  protected final int jumpBoost = Block.getHeight() * 2;
  protected boolean standing = true;

  public Entity(WorldWideLocation pos, int width, int height) {
    Entity.pos = pos;
    Entity.width = width;
    Entity.height = height;
  }

  public Entity(WWL pos, int width, int height) {
    Entity.pos = pos;
    Entity.width = width;
    Entity.height = height;
  }

  /**
   * shows the entity
   *
   * @param g
   */
  abstract public void draw(PGraphics g);

  /**
   * Updates the entity
   */
  public void tick() {
    if (!noClip) {
      calcCollision();
      addGravity();
      outOfBounds();
    }
    //This is need because of player can move with keep binds
    if(this instanceof Player == false) {
      calcSpeed();
    }
  }

  private void outOfBounds() {
    try {
      if (pos.getCanvas().getY() > 0) {
        pos.add(new LDVector(0, -pos.getCanvas().getY()), LocationTypes.canvas);
      } else if (pos.getGlobal().getY() >=World.get_HEIGHT()-1) {
        pos.setY(pos.getCanvas().getY() -1,LocationTypes.global);
        pos.setToCanvas();
      }
    } catch (MissingDataException e) {
      e.printStackTrace();
    }
  }

  private void calcCollision() {
    try {
      wallCollision();
      bottomCollision();
    } catch (MissingBlockTypeException | MissingDataException e) {
      e.printStackTrace();
    }
  }


  private void addGravity() {
    if(!gravity) return;
    speed.add(new LDVector(0, gravityAcc));
  }

  protected void calcSpeed() {
    speed.setX((int) (speed.getX() * airRes));
    speed.setY((int) (speed.getY() * airRes));
    pos.add(speed, LocationTypes.canvas);
  }

  private void wallCollision() throws MissingBlockTypeException {
    try {

      WWL tilePos = WorldWideLocation.create(pos.getCanvas(),LocationTypes.canvas);

      // WWL rightPos = WorldWideLocation.create(pos.getCanvas().getX() + Entity.width, pos.getGlobal().getY() + Entity.height / 2, LocationTypes.canvas, pos.getChunkID());

      // // Check right for player
      // if(World.getBlock(rightPos).getSolidity()) {
      //   if(pos.getCanvas().getX() + width > (rightPos.getCanvas().getX())) {
      //     speed.setX(0);
      //     Entity.pos.setPosition(new LDVector(Block.getWidth() - width - 1, pos.getCanvas().getY()), LocationTypes.canvas);
      //   }
      // }


      // There is a problem, that when the player moves over to the left or right the next block switchs so collsion can not be checked with that block
      WWL leftPos = WorldWideLocation.create(pos.getCanvas().getX() - Block.getWidth(), pos.getCanvas().getY(), LocationTypes.canvas, pos.getChunkID());
      // Check left for player
      if(World.getBlock(leftPos).getSolidity()) {
        if(pos.getCanvas().getX() < tilePos.getCanvas().getX()) {
          speed.setX(0);
          pos.setX(World.getBlock(leftPos).getCanvas().getX() + width, LocationTypes.canvas);
        }
      }


    } catch (MissingDataException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  private void bottomCollision() throws MissingBlockTypeException, MissingDataException {

      WWL leftCorner = WorldWideLocation.create(pos.getCanvas().getX() + width, pos.getCanvas().getY() + height, LocationTypes.canvas, pos.getChunkID());
      WWL rightCorner = WorldWideLocation.create(pos.getCanvas().getX(), pos.getCanvas().getY() + height, LocationTypes.canvas, pos.getChunkID());

      if(World.getBlock(leftCorner).getSolidity() || World.getBlock(rightCorner).getSolidity()) {
        gravity = false;
        standing = true;
        speed.setY(0);
        pos.setY(World.getBlock(rightCorner).getCanvas().getY() - Block.getHeight() + 1, LocationTypes.canvas);
        // pos.add(new LDVector(0,1),LocationTypes.global);
        // World.getBlock(rightCorner).getWWL().setToCanvas();
      } else {
        gravity = true;
        standing = false;
      }

    }

  private boolean playerRect(Block block) throws MissingDataException {
      var r1 = pos.getCanvas();
      var r2 = block.getCanvas();
      if (
      r1.getX() + Entity.width >= r2.getX() &&    // r1 right edge past r2 left
      r1.getX() <= r2.getX() + Block.getWidth() &&    // r1 left edge past r2 right
      r1.getY() + Entity.height >= r2.getY() &&    // r1 top edge past r2 bottom
      r1.getY() <= r2.getY() + Block.getHeight()) {    // r1 bottom edge past r2 top
        return true;
      }
    return false;
  }

  public static LDVector getCanvas() {
    try {
      return pos.getCanvas();
    } catch (MissingDataException e) {
      e.printStackTrace();
      return new LDVector(0, 0);
    }
  }
}
