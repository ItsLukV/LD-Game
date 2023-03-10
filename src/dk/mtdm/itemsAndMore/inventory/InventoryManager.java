package dk.mtdm.itemsAndMore.inventory;

import dk.mtdm.Pair;
import dk.mtdm.itemsAndMore.items.Item;
import dk.mtdm.itemsAndMore.items.ItemStack;
import dk.mtdm.itemsAndMore.items.ItemTypes;
import dk.mtdm.itemsAndMore.items.Pickaxe;
import dk.mtdm.misc.miscTextures.MiscTextures;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

public class InventoryManager {
  private final ItemStack[][] slots;
  private final Hotbar hotbar;
  private Pair<Integer, Integer> selected = new Pair<>(0,0);
  private boolean openMenu = false;

  /**
 * TODO: write javadoc
 */
  public InventoryManager() {
    slots = new ItemStack[5][3];
    for (int i = 0; i < slots.length; i++) {
      for (int j = 0; j < slots[i].length; j++) {
        slots[i][j] = new ItemStack(null);
      }
    }
    hotbar = new Hotbar();
  }
  /**
 * TODO: write javadoc
 */
  public void giveItem(Item item) {
    Pair<Integer, Integer> slot = null;
    try {
      slot = getSlot(item.getItemType());
    } catch (Exception e) {
      e.printStackTrace();
    }
    try {
      assert slot != null;
      slots[slot.getFirst()][slot.getSecond()].add(item);
    } catch (Exception e) {
      e.printStackTrace();

    }
  }

  public void giveItem(Item item, int amount) {
    Pair<Integer, Integer> slot = null;
    try {
      slot = getSlot(item.getItemType());
    } catch (Exception e) {
      e.printStackTrace();
    }
    try {
      slots[slot.getFirst()][slot.getSecond()].add(item, amount);
    } catch (Exception e) {
      e.printStackTrace();

    }
  }

  public void giveItemIntoHotbar(Item item) {
    try {
      hotbar.giveItem(new Pickaxe());
    } catch (Exception e) {
      e.printStackTrace();
    }

  }
  /**
 * TODO: write javadoc
 */
  private Pair<Integer, Integer> getSlot(ItemTypes type) throws Exception {
    for(int i = 0; i < slots.length; ++i) {
      for(int j = 0; j < slots[i].length; ++j) {
        if(slots[i][j].hasItem()) {
          if(slots[i][j].getItemType() == type) {
            return new Pair<>(i,j);
          }
        }
      }
    }

    for(int i = 0; i < slots.length; ++i) {
      for(int j = 0; j < slots[i].length; ++j) {
        if(!slots[i][j].hasItem()) {
          return new Pair<>(i,j);
        }
      }
    }
    throw new Exception("No empty slot");
  }

  public void draw(PGraphics g) {
    hotbar.show(g);
    if(openMenu) {
      // Shows the inventory
      PImage invImg = MiscTextures.getInventoryTexture();
      int x = g.width / 2 - invImg.width / 2;
      int y = g.height / 2 - invImg.height / 2;
      g.image(invImg,x,y);


      // Shows the selected item
      int slotSize = 64;
      int borderSize = 4;
      g.push();
      g.fill(0,255,0);
      int selectedX = x + borderSize + (borderSize * 2 + slotSize) * selected.getFirst();
      int selectedY = y + borderSize + (borderSize * 2 + slotSize) * selected.getSecond();
      g.rect(selectedX,selectedY, slotSize, slotSize);
      g.pop();

      // Shows the items
      for (int i = 0; i < slots.length; i++) {
        for (int j = 0; j < slots[i].length; j++) {
          if(!slots[i][j].hasItem()) continue;
          ItemStack item = slots[i][j];
          int imgX = x + borderSize + (borderSize * 2 + slotSize) * i;
          int imgY = y + borderSize + (borderSize * 2 + slotSize) * j;
          g.image(item.getItemTexture(),imgX,imgY,slotSize,slotSize);
          g.push();
          g.fill(0);
          g.textSize(15);
          g.text(item.getItemCount(),imgX+slotSize - 15,imgY+slotSize - 5);
          g.pop();
        }
      }
    }
  }

  public void tick() {
    hotbar.tick();
  }

  public void changeMenu() {
    openMenu = !openMenu;
  }

  public void mousePressed(PApplet p) {
    if(openMenu) {
      changeSelected(p);
    }
  }

  public void changeSelected(PApplet p) {
    selected = translateClick(p);

  }

  private Pair<Integer,Integer> translateClick(PApplet p) throws NullPointerException {
    int slotSize = 64;
    int borderSize = 4;
    PImage invImg = MiscTextures.getInventoryTexture();
    float mouseX = p.mouseX - (p.width / 2 - invImg.width / 2);
    float mouseY = p.mouseY - (p.height / 2 - invImg.height / 2);
    if (mouseX < 0 || mouseY < 0) return selected;
    if (mouseX > invImg.width || mouseY > invImg.height) return selected;
    int x = (int) (mouseX / (slotSize + borderSize * 2));
    int y = (int) (mouseY / (slotSize + borderSize * 2));
    return new Pair<Integer, Integer>(x, y);
  }
  public void swapSlot(int slot) {
        ItemStack stack = slots[selected.getFirst()][selected.getSecond()];
        slots[selected.getFirst()][selected.getSecond()] = hotbar.itemStacks[slot];
        hotbar.itemStacks[slot] = stack;
  }

  public boolean getOpenMenu() {
    return this.openMenu;
  }
}
