package dk.mtdm.itemsAndMore.inventory;

import dk.mtdm.itemsAndMore.items.ItemStack;
import dk.mtdm.itemsAndMore.items.Pickaxe;
import dk.mtdm.itemsAndMore.items.Stick;
import dk.mtdm.misc.miscTextures.MiscTextures;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

public class InventoryManager {
  private final ItemStack[][] slots = new ItemStack[5][3];
  private final Hotbar hotbar;
  private boolean openMenu = false;
  
  /**
 * TODO: write javadoc
 */
  public InventoryManager() {
    hotbar = new Hotbar();
    hotbar.setItem(0,new Pickaxe());
  }
  /**
 * TODO: write javadoc
 */
  public void giveItem() {
    int[] slot = getEmptySlot();
    assert slot != null;
    slots[slot[0]][slot[1]] = new ItemStack(new Stick());
  }
  /**
 * TODO: write javadoc
 */
  private int[] getEmptySlot() {
    int[] emptySlot = new int[2];
    for(int i = 0; i < slots.length; ++i) {
      for(int j = 0; j < slots[i].length; ++j) {
        if(slots[i][j] == null) {
          return new int[]{i,j}; 
        }
      }
    }
    return null;
  }

  public void draw(PGraphics g) {
    hotbar.show(g);
    if(openMenu) {
      PImage invImg = MiscTextures.getInventoryTexture();
      g.image(invImg,g.width / 2 - invImg.width / 2,g.height / 2 - invImg.height / 2);
    }
  }

  public void tick() {
    hotbar.tick();
  }

  public void changeMenu() {
    openMenu = !openMenu;
  }

  private void translateClick(PApplet p) {
    PImage invImg = MiscTextures.getInventoryTexture();
    int mouseX = p.mouseX - p.width / 2 - invImg.width / 2;
    int mouseY = p.mouseY - p.height / 2 - invImg.height / 2;
  }

}
