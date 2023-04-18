package dk.mtdm.itemsAndMore.Blocks;

import dk.mtdm.location.WWL;

public class AirBlock extends Block {
  /**
 * makes an airblock without the need for all pieces to be specified
 * @param pos the location of the airblock
 */
  public AirBlock(WWL pos) {
    super(pos, BlockTypes.air, false, false, false, null);
  }
  
}
