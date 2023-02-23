package dk.mtdm.itemsAndMore.Blocks;

import dk.mtdm.itemsAndMore.items.ItemTypes;
import dk.mtdm.location.LDVector;

public class GrassBlock extends Block{
  /**
 * TODO: write javadoc
 */
  public GrassBlock(LDVector pos) {
    super(pos, BlockTypes.grass, true, true, true, ItemTypes.grass);
  }
}
