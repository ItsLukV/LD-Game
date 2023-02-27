package dk.mtdm.itemsAndMore.Blocks;

import dk.mtdm.itemsAndMore.items.ItemTypes;
import dk.mtdm.location.WorldWideLocation;

public class StoneBlock extends Block{
  /**
 * TODO: write javadoc
 */
  public StoneBlock(WorldWideLocation pos) {
    super(pos, BlockTypes.stone, true, true, true, ItemTypes.stone);
  }
  
}
