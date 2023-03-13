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
import dk.mtdm.managementSystem.world.Chunk;
import dk.mtdm.managementSystem.world.ChunkList;
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
  private int moveSpeed = 4;
  private float airRes = 0.8f;
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

  public void tick() {
    calcInput();
    if(!noClip) {
      addGravity();
      try{
        calcCollision();
      }catch(MissingBlockTypeException e){
        e.printStackTrace();
      }
      try {
        if(pos.getCanvas().getY() > 0){
          pos.add(new LDVector(0, -pos.getCanvas().getY()), LocationTypes.canvas);
        }
      } catch (MissingDataException e) {
        e.printStackTrace();
      }
    }
    calcSpeed();
    inventory.tick();
  }

  private void calcSpeed() {
    speed.setX((int) (speed.getX() * airRes));
    speed.setY((int) (speed.getY() * airRes));

    pos.add(speed,LocationTypes.canvas);
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
  private void calcInput() {
    if (left) speed.add(new LDVector(-moveSpeed, 0));
    if (right) speed.add(new LDVector(moveSpeed, 0));
    if (up) speed.add(new LDVector(0, -moveSpeed));
    if (down) speed.add(new LDVector(0, moveSpeed));
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

  private void calcCollision() throws MissingBlockTypeException {
    System.out.println(speed.getX());
    {//up  and down
      {//up
        WorldWideLocation botLef = pos.copy();
        botLef.add(new LDVector(0,1), LocationTypes.canvas);
        WorldWideLocation botRig = pos.copy();
        botRig.add(new LDVector(Block.getWidth(),0), LocationTypes.canvas);
        botRig.add(new LDVector(0, 1),LocationTypes.canvas);
        if ((World.getBlock(botLef).getSolidity() || World.getBlock(botRig).getSolidity()) && speed.getY()>0){
          speed.setY(0);
        }
      }
      {//down
        WorldWideLocation topLef = pos.copy();
        topLef.add(new LDVector(0,-1-Block.getHeight()), LocationTypes.canvas);
        WorldWideLocation topRig = pos.copy();
        topRig.add(new LDVector(Block.getWidth(),Block.getHeight()), LocationTypes.canvas);
        topRig.add(new LDVector(0, -1),LocationTypes.canvas);
        if ((World.getBlock(topLef).getSolidity() || World.getBlock(topLef).getSolidity()) && speed.getY()<0){
          speed.setY(0);
        }
      }
    }
    {//right and left
      {//right
        WorldWideLocation rigBot = pos.copy();
        rigBot.add(new LDVector(Block.getWidth()*2+1,0), LocationTypes.canvas);
        
        WorldWideLocation rigTop = pos.copy();
        rigTop.add(new LDVector(Block.getWidth()*2+1,0), LocationTypes.canvas);
        if ((World.getBlock(rigBot).getSolidity() || World.getBlock(rigTop).getSolidity()) && speed.getX()>0){
          speed.setX(0);
        }
      }
      {//left
        WorldWideLocation lefBot = pos.copy();
        lefBot.add(new LDVector(Block.getWidth()+1,0), LocationTypes.canvas);
        
        WorldWideLocation lefTop = pos.copy();
        lefTop.add(new LDVector(Block.getWidth()+1,0), LocationTypes.canvas);
        if ((World.getBlock(lefBot).getSolidity() || World.getBlock(lefTop).getSolidity()) && speed.getX()<0){
          speed.setX(0);
        }
      }
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
