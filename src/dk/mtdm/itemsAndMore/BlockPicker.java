package dk.mtdm.itemsAndMore;

public class BlockPicker {
    static public Block picker(BlockTypes type,int x,int y) {
        return new Block(0, 0, type);
    }

}
