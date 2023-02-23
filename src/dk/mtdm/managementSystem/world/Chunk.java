package dk.mtdm.managementSystem.world;

import dk.mtdm.exceptions.MissingBlockTypeException;
import dk.mtdm.exceptions.MissingTextureException;
import dk.mtdm.itemsAndMore.Blocks.Block;
import dk.mtdm.itemsAndMore.Blocks.BlockPicker;
import dk.mtdm.itemsAndMore.Blocks.BlockTextures;
import dk.mtdm.itemsAndMore.Blocks.BlockTypes;
import dk.mtdm.location.LDVector;
import processing.core.PGraphics;
public class Chunk {
  final private int ID;
  private Block[][] containedBlocks;
  final private int creationHeight;
  private LDVector ChunkVector;
  private int chunkError = 0;
  WorldGenThread t;
  
  /**
   * creates a new chunk 
   * @param ID the ID of the chunk, used to calculate global coordinates
   * @param CHUNK_WIDTH the width of a chunk
   * @param CHUNK_HEIGHT the height of a chunk
   * @param seed the seed used to generate the world
   * @param maxCreation this controlls where the generation bias function changes
   */
  public Chunk(int ID, int CHUNK_WIDTH, int CHUNK_HEIGHT, int maxCreation){
    this.ID=ID;
    this.containedBlocks = new Block [CHUNK_WIDTH][CHUNK_HEIGHT];
    this.creationHeight = maxCreation;
    this.ChunkVector = new LDVector(this.ID*CHUNK_WIDTH, 0);
  }
  /**
   * @return the ID of this chunk
   */
  public int getID(){
    return ID;
  }
  /**
   * @param relativeLocation the relative location of a block 
   * @return the block stored at the given location
   */
  public Block getBlock(LDVector relativeLocation){
    return getBlock(relativeLocation.getX(), relativeLocation.getY());
  }
  /**
   * 
   * @param x the relative location of a block 
   * @param y the relative location of a block 
   * @return the block stored at the given location
   */
  public Block getBlock(int x, int y){
    if(containedBlocks[x][y] != null)
    return containedBlocks[x][y];
    
    if (t == null) {
      t = new WorldGenThread(ID, creationHeight, this);
    }
    if(!t.atWork){
      if(chunkError > 500){
        generate();
        chunkError = -1;
      }
      chunkError++;
      // System.out.print("\tfailed get block: "+ ID + ";(" + x +","+ y + ")");
    }
    if(containedBlocks[x][y] != null) return containedBlocks[x][y];
    
    LDVector tempBlockVector = new LDVector(x, y);
    tempBlockVector.add(ChunkVector);
    return BlockPicker.getAir(BlockTypes.air, tempBlockVector);
  }
  /**
   * resets the chunk by restarting the worldGenThread
   */
  public void generate(){
    if(t == null) t = new WorldGenThread(ID,creationHeight,this);
        if(!t.atWork){
      try {
        t.start();
      } catch (Exception e) {
        t=null;
        generate();
      }
    }
  }
  /**
   * chnages the block in the chunk
   * @param location the relative location of the chunk
   * @param block the block to place at the given location
   */
  public void setBlock(LDVector location,BlockTypes block){
    LDVector globalLocation = location.copy();
    globalLocation.add(ChunkVector);
    try {
      containedBlocks[location.getX()][location.getY()] = BlockPicker.picker(block,globalLocation);
    } catch (MissingBlockTypeException e) {
      e.printStackTrace();
    }
  }
  /**
   * @return a clone array of all blocks in the chunk
   */
  public Block[][] getAllBlocks(){
    return containedBlocks.clone();
  }
  /**
   * draws the chunk to the screen
   * @param g the drawing processer on which should be drawn
   */
  public void show(PGraphics g){
    for (int x = 0; x < containedBlocks.length;x++) {
      for (int y = 0; y < containedBlocks[x].length;y++) {
        getBlock(x,y).show(g); 
      }
    }
    border(g);
    LDVector progress = null;
    try {
      progress = t.progress.copy();
    } catch (NullPointerException e) {}
    if (progress == null) return;
    progress = World.GlobalToCanvas(progress);
    try {
      g.image(BlockTextures.picker(BlockTypes.inWork),progress.getX(),progress.getY(), Block.getWidth(), Block.getHeight());
    } catch (MissingTextureException e) {
      System.out.println(e);
    } catch (NullPointerException f){
      System.out.println(f);
    }
  }

  public void border(PGraphics g){
    g.push();
    g.noFill();
    g.stroke(255,0,0);
    g.rect(ID*World.get_CHUNK_WIDTH()*Block.getWidth(),0,World.get_CHUNK_WIDTH()*Block.getWidth(),-World.get_HEIGHT()*Block.getHeight());
    g.pop();
  } 
}
