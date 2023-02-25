package dk.mtdm.itemsAndMore.Blocks;

import dk.mtdm.location.WorldWideLocation;

public class BedrockBlock extends Block{
  /**
 * TODO: write javadoc
 */
  public BedrockBlock(WorldWideLocation pos) {
    super(pos, BlockTypes.bedrock,true,false,true,null);
  }
}
