package dk.mtdm.itemsAndMore.inventory;

import dk.mtdm.Sketch;
import dk.mtdm.itemsAndMore.items.Item;
import dk.mtdm.itemsAndMore.items.ItemStack;
import dk.mtdm.misc.miscTextures.MiscTextures;
import processing.core.PGraphics;
import processing.core.PImage;

public class Hotbar {
  public static int width = 208;
  public static int height = 72;
  private final int margin = 4;
  private final int slotSize = 64;
  private int activeSlot = 0;
  ItemStack[] itemStacks = new ItemStack[3];

  public Hotbar() {
    for (int i = 0; i < itemStacks.length; i++) {
      itemStacks[i] = new ItemStack(null);

//    itemStacks[i] = new ItemStack(new Stick());
    }
  }

  public void show(PGraphics g) {
    int x = g.width / 2 - width / 2;
    int y = g.height - height;
    // Show hotbar
    g.image(MiscTextures.getHotbarTexture(),x,y,width,height);

    // Show Items on hotbar
    for (int i = 0; i < itemStacks.length; i++) {
      if(!itemStacks[i].hasItem()) continue;
      PImage texture = itemStacks[i].getItemTexture();
      g.image(texture,x + (margin + slotSize) * i + margin,y + margin,slotSize,slotSize);
    }


    // Shows activeSlot
    g.push();
    g.strokeWeight(margin);
    g.stroke(0,255,0);
    g.noFill();
    g.rect(x  + (slotSize + margin) * activeSlot + margin / 2, y + margin / 2,slotSize + margin,slotSize + margin);
    g.pop();
  }

  public void tick() {
    keyPressed();
  }

  public void keyPressed() {
    switch (Sketch.KeyCode) {
      case 49 -> activeSlot = 0;
      case 50 -> activeSlot = 1;
      case 51 -> activeSlot = 2;
    }
  }

  public void setItem(int slot, Item item) {
    itemStacks[slot].setItem(item);
  }

  public void giveItem(Item item) throws Exception {
    for(int i = 0; i < itemStacks.length; i++) {
      if(itemStacks[i].hasItem()) continue;
      else {
        setItem(i, item);
        return;
      }
    }
    throw new Exception("No empty slot");
  }

}
