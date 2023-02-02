package dk.mtdm.managementSystem.Entitys;

import dk.mtdm.LDVector;
import dk.mtdm.itemsAndMore.Block;
import dk.mtdm.misc.miscTextures.MiscTextures;
import processing.core.PGraphics;

public class Player extends Entity {
  public int height = 32;
  public int width = 32;

  public Player(LDVector pos) {
    this.pos = pos;
  }

  @Override
  public void show(PGraphics g) {
    g.image(MiscTextures.getPlayerTexture(), pos.getX(), pos.getY());
  }

  @Override
  public void tick() {
    pos.add(speed);
  }

  /**
   * Checks if player collides with a block
   * 
   * @param block
   * @return
   */
  public boolean collisionWith(Block block) {
    LDVector blockPos = block.getPos();
    if (pos.getX() + width >= blockPos.getX() && // player right edge past block left
        pos.getX() <= blockPos.getX() + block.getWidth() && // player left edge past block right
        pos.getY() + height >= blockPos.getY() && // player top edge past block bottom
        pos.getY() <= blockPos.getY() + block.getHeight() // player bottom edge past block top
    ) {
      return true;
    }
    return false;
  }

}
