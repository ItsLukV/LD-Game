package dk.mtdm.itemsAndMore;

import dk.mtdm.LDVector;
import dk.mtdm.exceptions.MissingBlockTypeException;

public class BlockPicker {
	/**
	* Creates a block from BlockTypes
	* @param type the type of block
	* @param x,y sets x,y-canvas location value of the block
	*/
	static public Block picker(BlockTypes type,int x,int y) throws MissingBlockTypeException {
		return BlockPicker.picker(type, new LDVector(x, y));
	}

	/**
	* Creates a block from BlockTypes
	* @param pos Sets the canvas location of the block
	* @param id id/type of block
	*/
	static public Block picker(BlockTypes type, LDVector pos) throws MissingBlockTypeException{
		Block block = new Block(pos, type);		
		switch (type) {
			case air -> {
				block.setSolidity(false);
				block.setBreakability(false);
				block.setHoverability(false);
				block.setItemDrop(null);
				return block;
			}
			case grass -> {
				block.setSolidity(true);
				block.setBreakability(true);
				block.setHoverability(true);
				block.setItemDrop(ItemTypes.dirt);
				return block;
			}
			case dirt -> {
				block.setSolidity(true);
				block.setBreakability(true);
				block.setHoverability(true);
				block.setItemDrop(ItemTypes.dirt);
				return block;
			}
			case stone -> {
				block.setSolidity(true);
				block.setBreakability(true);
				block.setHoverability(true);
				block.setItemDrop(ItemTypes.stone);
				return block;
			}
			case bedrock -> {
				block.setSolidity(true);
				block.setBreakability(true);
				block.setHoverability(true);
				block.setItemDrop(null);
				return block;
			}
			default -> {
				throw new MissingBlockTypeException("This is not a block type!!: " + type.toString());
			}
		}
	}
}