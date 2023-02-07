package dk.mtdm.itemsAndMore.items;

public class ItemStack {
  private int itemCount;
  private final int maxSize;
  private final Item item;

  /**
 * TODO: write javadoc
 */
  public ItemStack(Item item) {
    maxSize = item.getStackSize();
    this.item = item;
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
}
