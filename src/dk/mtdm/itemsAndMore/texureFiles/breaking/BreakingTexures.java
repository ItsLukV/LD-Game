package dk.mtdm.itemsAndMore.texureFiles.breaking;

import processing.core.PApplet;
import processing.core.PImage;

public class BreakingTexures {
  private static PImage[] textures = new PImage[11];

  public static void loadBreakingTextures(PApplet p) {
    for (int i = 0; i < textures.length; i++) {
      String path = "src/dk/mtdm/itemsAndMore/texureFiles/breaking/destroy_stage_" + i + ".png";
      textures[i] = p.loadImage(path);
    }
  }

  public static PImage[] getBreakingTextures() {
    return textures;
  }

}
