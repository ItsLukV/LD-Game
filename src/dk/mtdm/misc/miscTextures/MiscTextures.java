package dk.mtdm.misc.miscTextures;

import processing.core.PApplet;
import processing.core.PImage;

public class MiscTextures {
  private static PImage player;
  private static PImage hotbar;
  private static PImage inventory;
  /**
   * TODO: write javadoc
   */
  public static void loadBlockTextures(PApplet p) {
    String path = "src/dk/mtdm/misc/miscTextures/";
    player = p.loadImage(path + "Player.png");
    hotbar = p.loadImage(path + "Hotbar.png");
    inventory = p.loadImage(path + "Inventory.png");
  }

  public static PImage getPlayerTexture() {
    return player;
  }

  public static PImage getHotbarTexture() {
    return hotbar;
  }

  public static PImage getInventoryTexture() {
    return inventory;
  }
}
