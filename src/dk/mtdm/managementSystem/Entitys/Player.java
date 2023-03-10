package dk.mtdm.managementSystem.Entitys;

import dk.mtdm.exceptions.MissingBlockTypeException;
import dk.mtdm.exceptions.MissingDataException;
import dk.mtdm.exceptions.MissingTextureException;
import dk.mtdm.itemsAndMore.Blocks.Block;
import dk.mtdm.itemsAndMore.items.Item;
import dk.mtdm.itemsAndMore.items.ItemStack;
import dk.mtdm.itemsAndMore.items.Pickaxe;
import dk.mtdm.itemsAndMore.items.Stick;
import dk.mtdm.itemsAndMore.texureFiles.BlockTextures;
import dk.mtdm.itemsAndMore.Blocks.BlockTypes;
import dk.mtdm.itemsAndMore.inventory.InventoryManager;
import dk.mtdm.location.LDVector;
import dk.mtdm.location.LocationTypes;
import dk.mtdm.location.WorldWideLocation;
import dk.mtdm.managementSystem.world.World;
import dk.mtdm.misc.miscTextures.MiscTextures;
import processing.core.PApplet;
import processing.core.PGraphics;

public class Player extends Entity {
  public static int height = 32;
  public static int width = 32;
  private boolean down;
  private boolean up;
  private boolean right;
  private boolean left;
  private final int moveSpeed = 5;
  private final float airRes = 0.8f;
  public static InventoryManager inventory = new InventoryManager();
  public static boolean noClip = false;
  public static int gravityAcc = 2;

  /**
   * Creates a player object
   * @param pos start pos
   */
  public Player(WorldWideLocation pos) {
    this.pos = pos;

    inventory.giveItemIntoHotbar(new Pickaxe()); // TODO remove this
  }

  @Override
  public void draw(PGraphics g) {
    g.push();
    try {
      g.image(MiscTextures.getPlayerTexture(), pos.getCanvas().getX(), pos.getCanvas().getY());
      g.strokeWeight(10);
      g.point(pos.getCanvas().getX(), pos.getCanvas().getY());
    } catch (MissingDataException e) {
      e.printStackTrace();
    }
    g.pop();

  }

  public void drawWithoutTranslate(PGraphics g) {
    inventory.draw(g);
  }
  /**
   * This is a method that updates the player (this needs to be updated every frame)
   */
  @Override
  public void tick(){
    tick(null);
  }

  public void tick(PGraphics g) {
    calcSpeed();
    if(!noClip) {
      addGravity();
      calcCollision(g);
      try {
        if(pos.getCanvas().getY() > 0){
          pos.add(new LDVector(0, -pos.getCanvas().getY()), LocationTypes.canvas);
        }
      } catch (MissingDataException e) {
        e.printStackTrace();
      }
    }
    inventory.tick();
  }

  /**
   * Checks if player collision
   *
   * @param block the block the player will check collision against
   * @return
   */
  public boolean collisionWith(Block block) {
    try{
      LDVector blockPos = block.getCanvas();
      if (pos.getCanvas().getX() + width >= blockPos.getX() && // player right edge past block left
          pos.getCanvas().getX() <= blockPos.getX() + Block.getWidth() && // player left edge past block right
          pos.getCanvas().getY() + height >= blockPos.getY() && // player top edge past block bottom
          pos.getCanvas().getY() <= blockPos.getY() + Block.getHeight() // player bottom edge past block top
      ) {
        return true;
      }
    }catch(MissingDataException e){
      e.printStackTrace();
    }
    return false;
  }
  /**
   * TODO: write javadoc
   */
  public void keyPressed(boolean left , boolean right, boolean up, boolean down, boolean e,boolean one,boolean two,boolean three) {
    if (left) this.left = true;
    if (right) this.right = true;
    if (up) this.up = true;
    if (down) this.down = true;
    if(e) inventory.changeMenu();
    if(one && inventory.getOpenMenu()) inventory.swapSlot(0);
    if(two && inventory.getOpenMenu()) inventory.swapSlot(1);
    if(three && inventory.getOpenMenu()) inventory.swapSlot(2);
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

    pos.add(speed,LocationTypes.canvas);
  }

  public LDVector getCanvas() {
    try {
      return pos.getCanvas();
    } catch (MissingDataException e) {
      e.printStackTrace();
      return new LDVector(0, 0);
    }
  }

  private void addGravity() {
    speed.add(new LDVector(0, gravityAcc));
  }

  private void calcCollision(PGraphics g) {
    Block block = null;
    try {
      block = World.getBlock(pos);
    } catch (MissingBlockTypeException e) {
      e.printStackTrace();
    }
//    if(!block.getSolidity()) {return; }
//    if(collisionWith(block)) {
    if(block.getSolidity()) {
      speed.setY(0);
      try {
        LDVector canvas = block.getCanvas();
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

  public InventoryManager getInventory() {
    return inventory;
  }

  public void mousePressed(PApplet p) {
    inventory.mousePressed(p);
  }

  public void swapSlot(int slot){
    inventory.swapSlot(slot);
  }
}
