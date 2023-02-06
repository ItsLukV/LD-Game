package dk.mtdm.itemsAndMore.inventory;

import dk.mtdm.itemsAndMore.items.ItemStack;

public class InventoryManager {
  private ItemStack[][] slots;
  public InventoryManager() {
    slots = new ItemStack[5][3];
  }

  public void giveItem() {
    int[] slot = getEmptySlot();
    slots[slot[0]][slot[1]] = new ItemStack(null);
  }

  private int[] getEmptySlot() {
    int[] slot = {-1,-1};

    return slot;
  }
}
