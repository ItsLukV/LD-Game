package dk.mtdm.itemsAndMore.Blocks;

import dk.mtdm.location.WWL;

public class BedrockBlock extends Block{
  /**
 * makes a BedrockBlock without the need for all pieces to be specified
 * @param pos the location of the BedrockBlock
 */
  public BedrockBlock(WWL pos) {
    super(pos, BlockTypes.bedrock,true,false,true,null);
  }
}
