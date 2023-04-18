package dk.mtdm.itemsAndMore.Blocks;

import dk.mtdm.itemsAndMore.items.ItemTypes;
import dk.mtdm.location.WWL;

public class GrassBlock extends Block{
  /**
 * makes a GrassBlock without the need for all pieces to be specified
 * @param pos the location of the GrassBlock
 */
  public GrassBlock(WWL pos) {
    super(pos, BlockTypes.grass, true, true, true, ItemTypes.grass);
  }
}
