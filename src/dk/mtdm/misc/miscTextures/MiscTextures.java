package dk.mtdm.misc.miscTextures;

import processing.core.PApplet;
import processing.core.PImage;

public class MiscTextures {
  private static PImage player;
  private static PImage hotbar;
  /**
   * TODO: write javadoc
   */
  public static void loadBlockTextures(PApplet p) {
    player = p.loadImage("src/dk/mtdm/misc/miscTextures/Player.png");
    hotbar = p.loadImage("src/dk/mtdm/misc/miscTextures/Hotbar.png");
  }

  public static PImage getPlayerTexture() {
    return player;
  }

  public static PImage getHotbarTexture() {
    return hotbar;
  }

}
