package dk.mtdm.itemsAndMore.items;

import dk.mtdm.itemsAndMore.ability.Ability;
import processing.core.PGraphics;

public abstract class Item {
  protected Ability ability;
  protected final int stackSize;
  protected final ItemTypes itemType;
  public Item(ItemTypes id,int stackSize) {
    this.itemType = id;
    this.stackSize = stackSize;
  }
  public abstract void tick();
  public abstract void show(PGraphics g);
  public int getStackSize() {
    return stackSize;
  }
  public ItemTypes getItemType() {
    return this.itemType;
  }
}
