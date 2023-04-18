package dk.mtdm.itemsAndMore.Blocks;

import dk.mtdm.itemsAndMore.items.ItemTypes;
import dk.mtdm.location.WWL;

public class DirtBlock extends Block{
  /**
 * makes a DirtBlock without the need for all pieces to be specified
 * @param pos the location of the DirtBlock
 */
  public DirtBlock(WWL pos) {
    super(pos, BlockTypes.dirt, true, true, true, ItemTypes.dirt);
  }
  
}
