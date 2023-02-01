package dk.mtdm.itemsAndMore;

// import java.util.concurrent.BlockingDeque;

import processing.core.PApplet;
import dk.mtdm.LDVector;

public class Block {
    private LDVector pos;
    private int width = 32;
    private int height = 32;
    private BlockTypes id;
    private boolean soild;
    private boolean breakability;
    private boolean hoverability;
    private ItemTypes itemDrop;

    /**
     * Creates a block
     * 
     * @param x  sets x-canvas location value of the block
     * @param y  sets y-canvas location value of the block
     * @param id id/type of block
     */
    public Block(int x, int y, BlockTypes id) {
        this.pos = new LDVector(x, y);
        this.id = id;
    }

    /**
     * Creates a block
     * 
     * @param pos Sets the canvas location of the block
     * @param id  id/type of block
     */
    public Block(LDVector pos, BlockTypes id) {
        this.pos = pos;
        this.id = id;
    }

    /**
     * shows the block
     * 
     * @param p
     */
    public void show(PApplet p) {
        try {
            p.image(BlockTextures.picker(id), pos.getX(), pos.getY(), width, height);
        } catch (Exception e) {
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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
