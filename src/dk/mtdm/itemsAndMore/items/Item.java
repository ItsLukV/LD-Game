package dk.mtdm.itemsAndMore.items;

import dk.mtdm.itemsAndMore.ability.Ability;
import processing.core.PGraphics;
import processing.core.PImage;

public abstract class Item {
  protected Ability ability;
  protected final int stackSize;
  protected final ItemTypes itemType;
  protected final PImage texture;

  /**
   * TODO: write javadoc
   */
  public Item(ItemTypes id, int stackSize, PImage texture) {
    this.itemType = id;
    this.stackSize = stackSize;
    this.texture = texture;
    this.ability = new Ability() {
      public void tick() {
      };

      public void selected() {
      };

      public void clicked() {
      }

      public void passive() {
      }

      public void show(PGraphics g) {
      };
    };
  }

  /**
   * TODO: write javadoc
   */
  public abstract void tick();

  /**
   * TODO: write javadoc
   */
  public int getStackSize() {
    return stackSize;
  }

  public ItemTypes getItemType() {
    return this.itemType;
  }

  public PImage getTexture() {
    return texture;
  }
}
