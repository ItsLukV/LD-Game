package dk.mtdm.itemsAndMore.Blocks;

import dk.mtdm.itemsAndMore.items.ItemTypes;
import dk.mtdm.location.WorldWideLocation;

public class GrassBlock extends Block{
  /**
 * TODO: write javadoc
 */
  public GrassBlock(WorldWideLocation pos) {
    super(pos, BlockTypes.grass, true, true, true, ItemTypes.grass);
  }
}
