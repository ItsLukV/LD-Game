package dk.mtdm.itemsAndMore.inventory;

import dk.mtdm.itemsAndMore.items.ItemStack;
import dk.mtdm.itemsAndMore.items.Stick;

public class InventoryManager {
  private ItemStack[][] slots;
  public InventoryManager() {
    slots = new ItemStack[5][3];
  }

  public void giveItem() {
    int[] slot = getEmptySlot();
    slots[slot[0]][slot[1]] = new ItemStack(new Stick());
  }

  private int[] getEmptySlot() {
    int[] emptySlot = {-1,-1};
    for(int i = 0; i < slots.length; ++i) {
      for(int j = 0; j < slots[i].length; ++j) {
        if(slots[i][j] == null) {
          return new int[]{i,j}; 
        }
      }
    }
    return emptySlot;
  }
}
