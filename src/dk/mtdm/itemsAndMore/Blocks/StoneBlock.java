package dk.mtdm.itemsAndMore.Blocks;

import dk.mtdm.LDVector;
import dk.mtdm.itemsAndMore.Block;
import dk.mtdm.itemsAndMore.BlockTypes;
import dk.mtdm.itemsAndMore.items.ItemTypes;

public class StoneBlock extends Block{

  public StoneBlock(LDVector pos) {
    super(pos, BlockTypes.stone, true, true, true, ItemTypes.stone);
  }
  
}
