package dk.mtdm.itemsAndMore;

import javax.swing.text.html.HTMLDocument.RunElement;

import dk.mtdm.LDVector;
import dk.mtdm.exceptions.MissingBlockTypeException;

public class BlockPicker {
	/**
	 * Creates a block from BlockTypes
	 * 
	 * @param type the type of block
	 * @param x,y  sets x,y-canvas location value of the block
	 */
	static public Block picker(BlockTypes type, int x, int y) throws MissingBlockTypeException {
		return BlockPicker.picker(type, new LDVector(x, y));
	}

	/**
	 * Returns a air block
	 * 
	 * @param type
	 * @param pos
	 * @return
	 */
	static public Block getAir(BlockTypes type, LDVector pos) {
		Block block = new Block(pos, type);
		block.setSolidity(false);
		block.setBreakability(false);
		block.setHoverability(false);
		block.setItemDrop(null);
		return block;
	}

	/**
	 * Returns a grass block
	 * 
	 * @param type
	 * @param pos
	 * @return
	 */
	static public Block getGrass(BlockTypes type, LDVector pos) {
		Block block = new Block(pos, type);
		block.setSolidity(true);
		block.setBreakability(true);
		block.setHoverability(true);
		block.setItemDrop(ItemTypes.dirt);
		return block;
	}

	/**
	 * Returns a dirt block
	 * 
	 * @param type
	 * @param pos
	 * @return
	 */
	static public Block getDirt(BlockTypes type, LDVector pos) {
		Block block = new Block(pos, type);
		block.setSolidity(true);
		block.setBreakability(true);
		block.setHoverability(true);
		block.setItemDrop(ItemTypes.dirt);
		return block;
	}

	/**
	 * Returns a stone block
	 * 
	 * @param type
	 * @param pos
	 * @return
	 */
	static public Block getStone(BlockTypes type, LDVector pos) {
		Block block = new Block(pos, type);
		block.setSolidity(true);
		block.setBreakability(true);
		block.setHoverability(true);
		block.setItemDrop(ItemTypes.stone);
		return block;
	}

	/**
	 * Returns a bedrock block
	 * 
	 * @param type
	 * @param pos
	 * @return
	 */
	static public Block getBedrock(BlockTypes type, LDVector pos) {
		Block block = new Block(pos, type);
		block.setSolidity(true);
		block.setBreakability(true);
		block.setHoverability(true);
		block.setItemDrop(null);
		return block;
	}

	/**
	 * Creates a block from BlockTypes
	 * 
	 * @param pos Sets the canvas location of the block
	 * @param id  id/type of block
	 */
	static public Block picker(BlockTypes type, LDVector pos) throws MissingBlockTypeException {
		return switch (type) {
			case air -> {
				yield BlockPicker.getAir(type, pos);
			}
			case grass -> {
				yield BlockPicker.getGrass(type, pos);
			}
			case dirt -> {
				yield BlockPicker.getDirt(type, pos);
			}
			case stone -> {
				yield BlockPicker.getStone(type, pos);
			}
			case bedrock -> {
				yield BlockPicker.getBedrock(type, pos);
			}
			default -> {
				throw new MissingBlockTypeException("This is not a block type!!: " + type.toString());
			}
		};
	}
}