package dk.mtdm.itemsAndMore.items;
//TODO: add comments

import javax.swing.text.html.HTMLDocument.RunElement;

public class ItemStack {
  private int itemCount;
  private final int maxSize;
  private final Item item;

  public ItemStack(Item item) {
    maxSize = item.getStackSize();
    this.item = item;
  }

  public void add(Item item) throws Exception {
    if(this.item.getItemType() != item.getItemType()) {
      throw new Exception("Not the same item type"); //TODO make custom Exception
    }
    if(itemCount + 1 > maxSize) {
      throw new Exception("Full itemstack"); //TODO make custom Exception
    }
    itemCount++;
  }

  public ItemType getItemType() {
    return item.getItemType();
  }
}
