package dk.mtdm.itemsAndMore.texureFiles;

import processing.core.PApplet;
import processing.core.PImage;

public class ItemTexture {
    private static PImage stick;
    public static PImage pickaxe;

    public static void loadBlockTextures(PApplet p) {
        String path = "src/dk/mtdm/itemsAndMore/texureFiles/";
        stick = p.loadImage(path + "stick.png");
        pickaxe = p.loadImage(path + "pickaxe.png");
    }

    public static PImage getStickTexture() {
        return stick;
    }

    public static PImage getPickaxeTexture() {
        return pickaxe;
    }
}
