package dk.mtdm.itemsAndMore.texureFiles;

import dk.mtdm.exceptions.MissingTextureException;
import dk.mtdm.itemsAndMore.Blocks.BlockTypes;
import processing.core.PApplet;
import processing.core.PImage;

public class BlockTextures {
    private static PImage air, bedrock, dirt, grass, stone,inWork;
    /**
     * loads and prepares all PImages for fast use in the program, is a central location for all textures so they don't get generated multiple times and don't need to be stored multiple times
     * @param p
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
     * returns the approptriet texture for the given block
     * @param type the type of the block to be drawn
     * @return the texture fitting the block
     * @throws MissingTextureException no texture was found for the given blocktype
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
