package dk.mtdm.itemsAndMore.ability;

import dk.mtdm.Pair;
import dk.mtdm.itemsAndMore.inventory.Hotbar;
import dk.mtdm.itemsAndMore.items.ItemStack;
import processing.core.PGraphics;

public class AbilityHandler {

  /**
   * Runs all passive abilities and up
   *
   * @param inventory
   * @param hotbar
   * @param selected
   */
  public static void tick(ItemStack[][] inventory, Hotbar hotbar, Pair<Integer, Integer> selected) {
    updatePassive(inventory, hotbar);
    ItemStack slot = hotbar.getItemStacks()[hotbar.getActiveSlot()];
    if (slot.hasItem())
      slot.getAbility().tick();
  }

  public static void show(PGraphics g, Hotbar hotbar) {
    ItemStack hotbarSlot = hotbar.getItemStacks()[hotbar.getActiveSlot()];
    if (hotbarSlot.hasItem())
      hotbarSlot.getAbility().show(g);
  }

  public static void updatePassive(ItemStack[][] inventory, Hotbar hotbar) {
    for (int i = 0; i < inventory.length; i++) {
      for (int j = 0; j < inventory[i].length; j++) {
        ItemStack slot = inventory[i][j];
        if (slot.hasItem())
          inventory[i][j].getAbility().passive();
      }
    }
    ItemStack[] hotBarStack = hotbar.getItemStacks();
    for (int i = 0; i < hotBarStack.length; i++) {
      if (hotBarStack[i].hasItem())
        hotBarStack[i].getAbility().passive();
    }
  }

  public static void mousePressedOnMenu(ItemStack[][] inventory, Pair<Integer, Integer> selected) {
    ItemStack slot = inventory[selected.getFirst()][selected.getSecond()];
    if (slot.hasItem())
      slot.getAbility().selected();
  }

  public static void mousePressedOnCanvas(Hotbar hotbar) {
    ItemStack slot = hotbar.getItemStacks()[hotbar.getActiveSlot()];
    if (slot.hasItem())
      slot.getAbility().clicked();
  }
}
