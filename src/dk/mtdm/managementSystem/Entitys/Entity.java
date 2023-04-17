package dk.mtdm.managementSystem.Entitys;

import dk.mtdm.exceptions.MissingBlockTypeException;
import dk.mtdm.exceptions.MissingDataException;
import dk.mtdm.itemsAndMore.Blocks.Block;
import dk.mtdm.location.LDVector;
import dk.mtdm.location.LocationTypes;
import dk.mtdm.location.WorldWideLocation;
import dk.mtdm.managementSystem.world.World;
import processing.core.PGraphics;

public abstract class Entity {
  protected static WorldWideLocation pos;
  public static int width;
  public static int height;
  protected LDVector speed = new LDVector(0, 0);
  protected int moveSpeed = 6;
  protected float airRes = 0.8f;
  public static boolean noClip = false;
  protected int gravityAcc = 2;
  protected boolean gravity = true;
  protected final int jumpBoost = Block.size.getY() * 2;
  protected boolean standing = true;

  public Entity(WorldWideLocation pos, int width, int height) {
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
      addGravity();
      try {
        calcCollision();
      } catch (MissingBlockTypeException | MissingDataException e) {
        e.printStackTrace();
      }
      try {
        if (pos.getCanvas().getY() > 0) {
          pos.add(new LDVector(0, -pos.getCanvas().getY()), LocationTypes.canvas);
        }
      } catch (MissingDataException e) {
        e.printStackTrace();
      }
    }
    calcSpeed();

  }

  private void calcCollision() throws MissingBlockTypeException, MissingDataException {
    bottomCollision();
  }


  private void addGravity() {
    if(!gravity) return;
    speed.add(new LDVector(0, gravityAcc));
  }




  private void calcSpeed() {
    speed.setX((int) (speed.getX() * airRes));
    speed.setY((int) (speed.getY() * airRes));
    pos.add(speed, LocationTypes.canvas);
  }


  private void bottomCollision() throws MissingBlockTypeException, MissingDataException {
      LDVector globalPlayerPos = Entity.pos.getGlobal();
      var blockPos = WorldWideLocation.create(globalPlayerPos.getX(), globalPlayerPos.getY(),LocationTypes.global);
      // System.out.println(World.getBlock(blockPos).getBlockType());
      // if(World.getBlock(blockPos).getSolidity()) return;
      boolean hit = playerRect(World.getBlock(blockPos));
      if(hit) {
        standing = true;
        gravity = false;
        // pos = WorldWideLocation.create(new LDVector(blockPos.getCanvas().getX(), blockPos.getCanvas().getY()), LocationTypes.canvas);
        this.speed = new LDVector(0, 0);
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
