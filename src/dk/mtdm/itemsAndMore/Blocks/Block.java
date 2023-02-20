package dk.mtdm.itemsAndMore.Blocks;

import processing.core.PGraphics;
import dk.mtdm.LDVector;
import dk.mtdm.exceptions.MissingTextureException;
import dk.mtdm.itemsAndMore.items.ItemTypes;
/**
 * @author @ItsLukV
 */
public abstract class Block {
    private LDVector pos;
    private static int width = 8;
    private static int height = 8;
    private BlockTypes id;
    protected boolean soild;
    protected boolean breakability;
    protected boolean hoverability;
    protected ItemTypes itemDrop;

    /**
     * Creates a block//TODO:redo comments
     * 
     * @param x  sets x-canvas location value of the block
     * @param y  sets y-canvas location value of the block
     * @param id id/type of block
     */
    public Block(LDVector pos, BlockTypes id, boolean soild, boolean breakability, boolean hoverability, ItemTypes itemDrop) {
        this.pos = pos;

        this.id = id;
        this.soild = soild;
        this.breakability = breakability;
        this.hoverability =  hoverability;
        this.itemDrop =  itemDrop;
    }

    /**
     * shows the block
     * 
     * @param g
     */
    public void show(PGraphics g) {
        try {
            g.image(BlockTextures.picker(id), pos.getX()*width,/* World.get_HEIGHT()*/height-pos.getY()*height, width, height);
        } catch (MissingTextureException e) {
            e.printStackTrace();
        }
    }

    // Getters and setters
    public void setSolidity(boolean soild) {
        this.soild = soild;
    }
    public boolean getSolidity() {
        return soild;
    }
    public void setBreakability(boolean breakability) {
        this.breakability = breakability;
    }
    public boolean isBreakable() {
        return breakability;
    }
    public void setHoverability(boolean hoverability) {
        this.hoverability = hoverability;
    }
    public boolean getHoverability() {
        return hoverability;
    }
    public void setItemDrop(ItemTypes itemDrop) {
        this.itemDrop = itemDrop;
    }
    public ItemTypes getItemDrop() throws NullPointerException {
        return this.itemDrop;
    }
    public LDVector getPos() {
        return pos;
    }
    public void setPos(LDVector vector) {
        this.pos = vector;
    }
    public static int getWidth() {
        return width;
    }
    public static void setWidth(int width) {
        Block.width = width;
    }
    public static int getHeight() {
        return height;
    }
    public static void setHeight(int height) {
        Block.height = height;
    }
}
