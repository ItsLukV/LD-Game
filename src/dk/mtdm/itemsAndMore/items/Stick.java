package dk.mtdm.itemsAndMore.items;

import dk.mtdm.itemsAndMore.texureFiles.ItemTexture;

public class Stick extends Item {

  /**
   * does nothing (placeholder item)
   */
  public Stick() {
    super(ItemTypes.stick, 99, ItemTexture.getStickTexture(), null);
  }

  /**
   * an eempty tick with no effect
   */
  public void tick() {

  }

}
