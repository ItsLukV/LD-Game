package dk.mtdm.managementSystem.Entitys;

import javax.security.auth.x500.X500Principal;

import dk.mtdm.LDVector;
import dk.mtdm.exceptions.MissingTextureException;
import dk.mtdm.itemsAndMore.Blocks.Block;
import dk.mtdm.itemsAndMore.Blocks.BlockTextures;
import dk.mtdm.itemsAndMore.Blocks.BlockTypes;
import dk.mtdm.itemsAndMore.inventory.InventoryManager;
import dk.mtdm.managementSystem.world.World;
import dk.mtdm.misc.miscTextures.MiscTextures;
import processing.core.PGraphics;

public class Player extends Entity {
  public static int height = 32;
  public static int width = 32;
  private boolean down;
  private boolean up;
  private boolean right;
  private boolean left;
  private int moveSpeed = 5;
  private float airRes = 0.8f;
  private InventoryManager inventory = new InventoryManager();
  public static boolean noClip = false;
  public static int gravityAcc = 2;

  /**
   * Creates a player object
   * @param pos start pos
   */
  public Player(LDVector pos) {
    this.pos = pos;
    inventory.giveItem();
  }
  /**
   * Creates a player object
   * @param pos start pos
   */
  @Override
  public void show(PGraphics g) {
    g.push();
    g.image(MiscTextures.getPlayerTexture(), pos.getX(), pos.getY());
    g.strokeWeight(10);
    g.point(pos.getX(), pos.getY());
    g.pop();
  }

  /**
   * This is a method that updates the player (this needs to be updated every frame)
   */
  @Override
  public void tick(){
    calcSpeed();
    if(!noClip) {
      addGravity();
      calcCollision(null);
    }
  }

  public void tick(PGraphics g) {
    calcSpeed();
    if(!noClip) {
      addGravity();
      calcCollision(g);
    }
  }

  /**
   * Checks if player collision
   * 
   * @param block the block the player will check collision against
   * @return
   */
  public boolean collisionWith(Block block) {
    LDVector blockPos = block.getPos();
    if (pos.getX() + width >= blockPos.getX() && // player right edge past block left
        pos.getX() <= blockPos.getX() + Block.getWidth() && // player left edge past block right
        pos.getY() + height >= blockPos.getY() && // player top edge past block bottom
        pos.getY() <= blockPos.getY() + Block.getHeight() // player bottom edge past block top
    ) {
      return true;
    }
    return false;
  }
  /**
   * TODO: write javadoc
   */
  public void keyPressed(boolean left , boolean right, boolean up, boolean down) {
    if (left) this.left = true;
    if (right) this.right = true;
    if (up) this.up = true;
    if (down) this.down = true;
  }
  /**
   * TODO: write javadoc
   */
  public void keyReleased(boolean left , boolean right, boolean up, boolean down) {
    if (left) this.left = false;
    if (right) this.right = false;
    if (up) this.up = false;
    if (down) this.down = false;
  }
  /**
   * TODO: write javadoc
   */
  private void calcSpeed() {
    if (left) speed.add(new LDVector(-moveSpeed, 0));
    if (right) speed.add(new LDVector(moveSpeed, 0));
    if (up) speed.add(new LDVector(0, -moveSpeed));
    if (down) speed.add(new LDVector(0, moveSpeed));

    speed.setX((int) (speed.getX() * airRes));
    speed.setY((int) (speed.getY() * airRes));

    pos.add(speed);
  }

  public LDVector getPos() {
    return pos;
  }

  private void addGravity() {
    speed.add(new LDVector(0, gravityAcc));
  }

  private void calcCollision(PGraphics g) {
    Block block = World.getBlockCanvas(new LDVector(pos.getX(), pos.getY()));
    if(block.getSolidity()) {
      speed.setY(0);
      try {
        LDVector canvas = World.GlobalToCanvas(block.getPos());
        g.image(BlockTextures.picker(BlockTypes.inWork),canvas.getX(),
                canvas.getY(), Block.getWidth(), Block.getHeight());
        // System.out.println(canvas.getX() + " " + canvas.getY());
        // System.out.println((float)this.getPos().getX()/(float)World.getBlockCanvas(new LDVector(pos.getX(), pos.getY())).getPos().getX());
      } catch (MissingTextureException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (Exception e){}
    }
  }
}
