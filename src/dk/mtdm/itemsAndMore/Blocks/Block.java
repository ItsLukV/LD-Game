package dk.mtdm.itemsAndMore.Blocks;

import dk.mtdm.itemsAndMore.texureFiles.BlockTextures;
import dk.mtdm.itemsAndMore.texureFiles.breaking.BreakingTexures;
import processing.core.PGraphics;
import dk.mtdm.exceptions.MissingBlockTypeException;
import dk.mtdm.exceptions.MissingDataException;
import dk.mtdm.exceptions.MissingTextureException;
import dk.mtdm.itemsAndMore.items.ItemTypes;
import dk.mtdm.location.LDVector;
import dk.mtdm.location.LocationTypes;
import dk.mtdm.location.WWL;

/**
 * @author @ItsLukV
 */
public abstract class Block {
    private WWL pos;
    private static LDVector size = new LDVector(32, 32);
    private BlockTypes id;
    protected boolean soild;
    protected boolean breakability;
    protected boolean hoverability;
    protected ItemTypes itemDrop;
    private int breakLevel = 0;

    /**
     * creates a block based on the given paramiters
     * @param pos the location of the block
     * @param id the type of block
     * @param soild should the block have collision
     * @param breakability should the block be able to be broken by the player
     * @param hoverability should something special happen if you hover the mouse over the block
     * @param itemDrop what should be droped when the block is mined
     */
    public Block(WWL pos, BlockTypes id, boolean soild, boolean breakability, boolean hoverability, ItemTypes itemDrop) {
        this.pos = pos;

        this.id = id;
        this.soild = soild;
        this.breakability = breakability;
        this.hoverability = hoverability;
        this.itemDrop = itemDrop;
    }
    public static Block fromState(String state,WWL pos) throws MissingBlockTypeException{
        String[] in = state.split(",");
        int[] input = new int[in.length];
        for (int j = 0; j < in.length; j++) {
            input[j] = Integer.parseInt(in[j]);
        }
        Block out = BlockPicker.picker(BlockTypes.values()[input[0]], pos);
        out.setSolidity(input[1] == 1);
        out.setBreakability(input[2] == 1);
        out.setHoverability(input[3] == 1);
        if(input[4] == -1){
            out.setItemDrop(ItemTypes.values()[input[4]]);
        }
        return out;
    }
    /**
     * shows the block
     *
     * @param g
     */
    public void show(PGraphics g) {
        try {
            try {
                g.image(BlockTextures.picker(id), pos.getCanvas().getX(),
                        /* World.get_HEIGHT() */size.getY() - pos.getCanvas().getY(), size.getX(), size.getY());
                if (breakLevel != 0)
                    g.image(BreakingTexures.getBreakingTextures()[breakLevel], pos.getCanvas().getX(),
                            size.getY() - pos.getCanvas().getY(), size.getX(), size.getY());
            } catch (MissingDataException e) {
                e.printStackTrace();
            }
        } catch (MissingTextureException e) {
            e.printStackTrace();
        }
    }

    // Getters and setters
    public BlockTypes getBlockType() {
        return id;
    }

    public void setBreakLvl(int level) {
        breakLevel = level;
    }

    public int getBreakLevel() {
        return breakLevel;
    }

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

    public LDVector getCanvas() {
        try {
            return this.pos.getCanvas();
        } catch (MissingDataException e) {
            e.printStackTrace();
            return new LDVector(0, 0);
        }
    }

    public LDVector getGlobal() {
        try {
            return this.pos.getGlobal();
        } catch (MissingDataException e) {
            e.printStackTrace();
            return new LDVector(0, 0);
        }
    }

    public void setCanvas(LDVector vector) {
        this.pos.setPosition(vector, LocationTypes.canvas);
    }

    public static int getWidth() {
        return size.getX();
    }

    public static void setWidth(int width) {
        size.setX(width);
    }

    public static int getHeight() {
        return size.getY();
    }

    public static void setHeight(int height) {
        size.setY(height);
    }

    public void setChunkID(int chunkID) {
        this.pos.setChunkID(chunkID);
    }

    /**
     *
     * @return
     *         [0] topLeft,
     *         [1] bottomLeft,
     *         [2] topRight,
     *         [2] bottomRight
     * @throws MissingDataException
     */
    public LDVector[] getCorners() throws MissingDataException {
        LDVector[] out = new LDVector[4];
        out[0] = new LDVector(pos.getCanvas().getX(), pos.getCanvas().getY());
        out[1] = new LDVector(pos.getCanvas().getX(), pos.getCanvas().getY() - Block.getHeight());

        out[2] = new LDVector(pos.getCanvas().getX() + Block.getWidth(), pos.getCanvas().getY());
        out[3] = new LDVector(pos.getCanvas().getX() + Block.getWidth(), pos.getCanvas().getY() - Block.getHeight());
        return out;
    }

    public String getState() {
        String out = "";
            out+= (id != null ? id.ordinal() : -1) + "," + (soild ? 0:1) + "," +  (breakability ? 0:1) + "," + (hoverability ? 0:1) + "," + (itemDrop != null ? itemDrop.ordinal() : -1);
        return out;
    }
}
