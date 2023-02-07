package dk.mtdm.itemsAndMore.Blocks;

import dk.mtdm.LDVector;
import dk.mtdm.itemsAndMore.Block;
import dk.mtdm.itemsAndMore.BlockTypes;

public class BedrockBlock extends Block{
  /**
 * TODO: write javadoc
 */
  public BedrockBlock(LDVector pos) {
    super(pos, BlockTypes.bedrock,true,false,true,null);
  }
}
