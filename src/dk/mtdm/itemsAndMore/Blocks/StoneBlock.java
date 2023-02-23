package dk.mtdm.itemsAndMore.Blocks;

import dk.mtdm.itemsAndMore.items.ItemTypes;
import dk.mtdm.location.LDVector;

public class StoneBlock extends Block{
  /**
 * TODO: write javadoc
 */
  public StoneBlock(LDVector pos) {
    super(pos, BlockTypes.stone, true, true, true, ItemTypes.stone);
  }
  
}
