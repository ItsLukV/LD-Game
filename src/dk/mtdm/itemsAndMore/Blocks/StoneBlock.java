package dk.mtdm.itemsAndMore.Blocks;

import dk.mtdm.itemsAndMore.items.ItemTypes;
import dk.mtdm.location.WWL;

public class StoneBlock extends Block{
  /**
 * makes a StoneBlock without the need for all pieces to be specified
 * @param pos the location of the StoneBlock
 */
  public StoneBlock(WWL pos) {
    super(pos, BlockTypes.stone, true, true, true, ItemTypes.stone);
  }
  
}
