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
  protected static boolean noClip = false;
  protected static int gravityAcc = 2;
  protected boolean gravity = true;
  protected final int jumpBoost = Block.size.getY() * 2;
  protected boolean standing = true;



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
      LDVector globalPlayerPos = pos.getGlobal();
      var blockPos = WorldWideLocation.create(globalPlayerPos.getX(), globalPlayerPos.getY(),LocationTypes.global);
      // System.out.println(World.getBlock(blockPos).getBlockType());
      if(World.getBlock(blockPos).getSolidity()) return;

      // if(playerRect(World.getBlock(blockPos))) {
        standing = true;
        gravity = false;
        Player.pos = WorldWideLocation.create(new LDVector(blockPos.getCanvas().getX(), blockPos.getCanvas().getY()), LocationTypes.canvas);
        this.speed = new LDVector(0, 0);
      // }
  }

  private boolean playerRect(Block block) throws MissingDataException {
      if (pos.getCanvas().getX() + width >= block.getCanvas().getX() &&    // r1 right edge past r2 left
      pos.getCanvas().getX() <= block.getCanvas().getX() + Block.getWidth() &&    // r1 left edge past r2 right
      pos.getCanvas().getY() + height >= block.getCanvas().getY() &&    // r1 top edge past r2 bottom
      pos.getCanvas().getY() <= block.getCanvas().getY() + Block.getHeight()) {    // r1 bottom edge past r2 top
        return true;
      }
    return false;
  }
// Getters and setters
  public static WorldWideLocation getPos() {
    return pos;
  }
}
