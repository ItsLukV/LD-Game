package dk.mtdm.itemsAndMore;
//TODO: add comments
import dk.mtdm.exceptions.MissingTextureException;
import processing.core.PApplet;
import processing.core.PImage;

public class BlockTextures {
    private static PImage air, bedrock, dirt, grass, stone;

    public static void loadBlockTextures(PApplet p) {
        String path = "src/dk/mtdm/itemsAndMore/texureFiles/";
        air = p.loadImage(path+"air.png");
        bedrock = p.loadImage(path+"bedrock.png");
        dirt = p.loadImage(path+"dirt.png");
        grass = p.loadImage(path+"grass.png");
        stone = p.loadImage(path+"stone.png");
    }
    public static PImage picker(BlockTypes type) throws MissingTextureException {
        switch(type) {
            case air:
                return air;
            case bedrock:
                return bedrock;
            case dirt:
                return dirt;
            case grass: 
                return grass;
            case stone:
                return stone;
            default: 
                throw new MissingTextureException(type.name() + " does not have a texture");
        }
    }
}
