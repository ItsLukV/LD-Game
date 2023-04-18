package dk.mtdm.itemsAndMore.items;

import dk.mtdm.exceptions.MissingItemType;
import dk.mtdm.itemsAndMore.ability.Ability;
import processing.core.PImage;

public class ItemStack {
  private int itemCount = 0;
  private int maxSize;
  private Item item;

  /**
   * a group of items with the same type
   * @param item the first item to be in the stack, also determines the intire stacks type
   */
  public ItemStack(Item item) {
    if (item == null) {
      return;
    }
    this.item = item;
    maxSize = item.getStackSize();
  }

  /**
   * add another item to the stack, this will also check for compatibility
   */
  public void add(Item item) throws MissingItemType,IndexOutOfBoundsException {
    if (this.item == null) {
      this.item = item;
      maxSize = item.getStackSize();
    }
    if (this.item.getItemType() != item.getItemType()) {
      throw new MissingItemType("Not the same item type"); // TODO make custom Exception
    }
    if (!(itemCount < maxSize)) {
      throw new IndexOutOfBoundsException("Full itemstack"); // TODO make custom Exception
    }
    itemCount += 1;
  }

  public void add(Item item, int amount) throws Exception {
    if (this.item == null) {
      this.item = item;
      maxSize = item.getStackSize();
    }
    if (this.item.getItemType() != item.getItemType()) {
      throw new Exception("Not the same item type"); // TODO make custom Exception
    }
    if (!(itemCount < maxSize)) {
      throw new Exception("Full itemstack"); // TODO make custom Exception
    }
    itemCount += amount;
  }

  /**
   * TODO: write javadoc
   */
  public ItemTypes getItemType() {
    return item.getItemType();
  }

  public PImage getItemTexture() {
    return item.getTexture();
  }

  /**
   * Returns if the item is null or not
   *
   * @return
   */
  public boolean hasItem() {
    return item != null;
  }

  public void setItem(Item item) {
    this.item = item;
  }

  public int getItemCount() {
    return itemCount;
  }

  public Ability getAbility() {
    return item.ability;
  }
}
