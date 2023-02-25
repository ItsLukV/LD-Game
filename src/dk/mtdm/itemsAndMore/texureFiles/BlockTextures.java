package dk.mtdm.itemsAndMore.texureFiles;

import dk.mtdm.exceptions.MissingTextureException;
import dk.mtdm.itemsAndMore.Blocks.BlockTypes;
import processing.core.PApplet;
import processing.core.PImage;

public class BlockTextures {
    private static PImage air, bedrock, dirt, grass, stone,inWork;
    /**
   * TODO: write javadoc
   */
    public static void loadBlockTextures(PApplet p) {
        String path = "src/dk/mtdm/itemsAndMore/texureFiles/";
        air = p.loadImage(path+"air.png");
        bedrock = p.loadImage(path+"bedrock.png");
        dirt = p.loadImage(path+"dirt.png");
        grass = p.loadImage(path+"grass.png");
        stone = p.loadImage(path+"stone.png");
        inWork = p.loadImage(path+"work.png");
    }
    /**
   * TODO: write javadoc
   */
    public static PImage picker(BlockTypes type) throws MissingTextureException {
        return switch (type) {
            case air -> air;
            case bedrock -> bedrock;
            case dirt -> dirt;
            case grass -> grass;
            case stone -> stone;
            case inWork -> inWork;
            default -> throw new MissingTextureException(type.name() + " does not have a texture");
        };
    }
}
