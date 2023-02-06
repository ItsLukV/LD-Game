package dk.mtdm.itemsAndMore.Blocks;

import dk.mtdm.LDVector;
import dk.mtdm.itemsAndMore.Block;
import dk.mtdm.itemsAndMore.BlockTypes;
import dk.mtdm.itemsAndMore.items.ItemTypes;

public class GrassBlock extends Block{

  public GrassBlock(LDVector pos) {
    super(pos, BlockTypes.grass, true, true, true, ItemTypes.grass);
  }
  
}
