package dk.mtdm.misc.miscTextures;

import processing.core.PApplet;
import processing.core.PImage;

public class MiscTextures {
  private static PImage player;

  public static void loadBlockTextures(PApplet p) {
    player = p.loadImage("src/dk/mtdm/misc/miscTextures/Player.png");
  }

  public static PImage getPlayerTexture() {
    return player;
  }
}
