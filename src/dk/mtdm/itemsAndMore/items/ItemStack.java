package dk.mtdm.itemsAndMore.items;

import processing.core.PImage;

public class ItemStack {
  private int itemCount;
  private int maxSize;
  private Item item;

  /**
 * TODO: write javadoc
 */
  public ItemStack(Item item) {
    if(item == null) {return;}
    this.item = item;
    maxSize = item.getStackSize();
  }
  /**
 * TODO: write javadoc
 */
  public void add(Item item) throws Exception {
    if(this.item.getItemType() != item.getItemType()) {
      throw new Exception("Not the same item type"); //TODO make custom Exception
    }
    if(itemCount + 1 > maxSize) {
      throw new Exception("Full itemstack"); //TODO make custom Exception
    }
    itemCount++;
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
   * @return
   */
  public boolean hasItem() {
    return item == null;
  }

  public void setItem(Item item) {
    this.item = item;
  }
}
