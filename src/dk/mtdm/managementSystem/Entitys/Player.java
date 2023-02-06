package dk.mtdm.managementSystem.Entitys;

import dk.mtdm.LDVector;
import dk.mtdm.itemsAndMore.Block;
import dk.mtdm.misc.miscTextures.MiscTextures;
import processing.core.PGraphics;

public class Player extends Entity {
  public static int height = 32;
  public static int width = 32;
  private boolean down;
  private boolean up;
  private boolean right;
  private boolean left;
  private int moveSpeed = 2;
  private float airRes = 0.8f;

  public Player(LDVector pos) {
    this.pos = pos;
  }

  @Override
  public void show(PGraphics g) {
    g.image(MiscTextures.getPlayerTexture(), pos.getX(), pos.getY());
  }

  @Override
  public void tick() {
    calcSpeed();
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

  public void keyPressed(boolean left , boolean right, boolean up, boolean down) {
    if (left) this.left = true;
    if (right) this.right = true;
    if (up) this.up = true;
    if (down) this.down = true;
  }

  public void keyReleased(boolean left , boolean right, boolean up, boolean down) {
    if (left) this.left = false;
    if (right) this.right = false;
    if (up) this.up = false;
    if (down) this.down = false;
  }

  private void calcSpeed() {
    if (left) speed.add(new LDVector(-moveSpeed, 0));
    if (right) speed.add(new LDVector(moveSpeed, 0));
    if (up) speed.add(new LDVector(0, -moveSpeed));
    if (down) speed.add(new LDVector(0, moveSpeed));

    speed.setX((int) (speed.getX() * airRes));
    speed.setY((int) (speed.getY() * airRes));

    pos.add(speed);
  }

  public LDVector getPos() {
    return pos;
  }
}
