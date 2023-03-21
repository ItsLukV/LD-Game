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
  public Item(ItemTypes id, int stackSize, PImage texture, Ability ability) {
    this.itemType = id;
    this.stackSize = stackSize;
    this.texture = texture;
    if (ability == null) {
      this.ability = new Ability() {
        public void tick() {
        };

        public void selected() {
        };

        public void passive() {
        }

        public void show(PGraphics g) {
        }

        @Override
        public void clicked(int x, int y) {
          // TODO Auto-generated method stub
          throw new UnsupportedOperationException("Unimplemented method 'clicked'");
        };
      };
    } else {
      this.ability = ability;
    }
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
