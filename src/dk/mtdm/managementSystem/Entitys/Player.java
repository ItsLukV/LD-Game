package dk.mtdm.managementSystem.Entitys;

import dk.mtdm.itemsAndMore.items.Pickaxe;
import dk.mtdm.itemsAndMore.inventory.InventoryManager;
import dk.mtdm.location.LDVector;
import dk.mtdm.location.WorldWideLocation;
import dk.mtdm.misc.miscTextures.MiscTextures;
import processing.core.PApplet;
import processing.core.PGraphics;

public class Player extends Entity {
  private boolean down;
  private boolean up;
  private boolean right;
  private boolean left;
  public static WorldWideLocation pos;
  public static InventoryManager inventory = new InventoryManager();

  /**
   * Creates a player object
   *
   * @param pos start pos
   */
  public Player(WorldWideLocation pos) {
    super(pos, 32, 32);
  }

  @Override
  public void draw(PGraphics g) {
    g.push();
    g.imageMode(PApplet.CENTER);
    g.image(MiscTextures.getPlayerTexture(), Player.getCanvas().getX(), Player.getCanvas().getY());
    g.pop();

  }

  public void drawWithoutTranslate(PGraphics g) {
    inventory.draw(g);
  }


  /**
   * This is a method that updates the player (this needs to be updated every
   * frame)
   */
  @Override
  public void tick() {
    super.tick();
    calcInput();
    calcSpeed();

    inventory.tick();
  }



  /**
   * TODO: write javadoc
   */
  public void keyPressed(boolean left, boolean right, boolean up, boolean down, boolean e, boolean one, boolean two,
      boolean three) {
    if (left)
      this.left = true;
    if (right)
      this.right = true;
    if (up)
      this.up = true;
    if (down)
      this.down = true;
    if (e)
      inventory.changeMenu();
    if (one && inventory.getOpenMenu())
      inventory.swapSlot(0);
    if (two && inventory.getOpenMenu())
      inventory.swapSlot(1);
    if (three && inventory.getOpenMenu())
      inventory.swapSlot(2);
  }

  /**
   * TODO: write javadoc
   */
  public void keyReleased(boolean left, boolean right, boolean up, boolean down) {
    if (left)
      this.left = false;
    if (right)
      this.right = false;
    if (up) {
      this.up = false;
      this.standing = false;
    }
    if (down)
      this.down = false;
  }

  /**
   * TODO: write javadoc
   */
  private void calcInput() {
    if (left)
      speed.add(new LDVector(-moveSpeed, 0));
    if (right)
      speed.add(new LDVector(moveSpeed, 0));
    if (up && (standing || noClip)) {
      speed.add(new LDVector(0, -jumpBoost));
    }
    if (down)
      speed.add(new LDVector(0, moveSpeed));
  }




  // private void calcCollision() throws MissingBlockTypeException, MissingDataException {

    // bottomCollision();
    // {// up and down
    //   {// down
    //     WorldWideLocation botLef = pos.copy();
    //     botLef.add(new LDVector(0, 1), LocationTypes.canvas);
    //     WorldWideLocation botRig = pos.copy();
    //     botRig.add(new LDVector(Block.getWidth(), 1), LocationTypes.canvas);
    //     if ((World.getBlock(botLef).getSolidity() || World.getBlock(botRig).getSolidity()) && speed.getY() > 0) {
    //       speed.setY(0);
    //       standing = true;
    //       jumpTime = 0;
    //     }
    //   }
    //   {// up
    //     WorldWideLocation topLef = pos.copy();
    //     topLef.add(new LDVector(0, -1 - Block.getHeight()), LocationTypes.canvas);
    //     WorldWideLocation topRig = pos.copy();
    //     topRig.add(new LDVector(Block.getWidth(), -1 - Block.getHeight()), LocationTypes.canvas);
    //     if ((World.getBlock(topLef).getSolidity() || World.getBlock(topRig).getSolidity()) && speed.getY() < 0) {
    //       if (!standing) {
    //         speed.setY(0);
    //       }
    //     }
    //   }
    // }
    {// right and left
     // TODO: fix, it broke
     // {//right
     // WorldWideLocation rigBot = pos.copy();
     // rigBot.add(new LDVector(Block.getWidth()*2+1,0), LocationTypes.canvas);

      // WorldWideLocation rigTop = pos.copy();
      // rigTop.add(new LDVector(Block.getWidth()*2+1,0), LocationTypes.canvas);
      // if ((World.getBlock(rigBot).getSolidity() ||
      // World.getBlock(rigTop).getSolidity()) && speed.getX()>0){
      // speed.setX(0);
      // }
      // }
      // {//left
      // WorldWideLocation lefBot = pos.copy();
      // lefBot.add(new LDVector(Block.getWidth()+1,0), LocationTypes.canvas);

      // WorldWideLocation lefTop = pos.copy();
      // lefTop.add(new LDVector(Block.getWidth()+1,0), LocationTypes.canvas);
      // if ((World.getBlock(lefBot).getSolidity() ||
      // World.getBlock(lefTop).getSolidity()) && speed.getX()<0){
      // speed.setX(0);
      // }
      // }
    }
  // }


    public static WorldWideLocation getPos() {
      return pos;
    }

  public InventoryManager getInventory() {
    return inventory;
  }

  public void mousePressed(PApplet p) {
    inventory.mousePressed(p);
  }

  public void swapSlot(int slot) {
    inventory.swapSlot(slot);
  }
}
