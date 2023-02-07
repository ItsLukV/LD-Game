package dk.mtdm.managementSystem.world;

import dk.mtdm.LDVector;
import dk.mtdm.exceptions.MissingBlockTypeException;
import dk.mtdm.itemsAndMore.Block;
import dk.mtdm.itemsAndMore.BlockPicker;
import dk.mtdm.itemsAndMore.BlockTypes;
import processing.core.PGraphics;
public class Chunk {
  final private int ID;
  private Block[][] containedBlocks;
  final private int seed;
  final private int creationHeight;
  private LDVector ChunkVector;
  WorldGenThread t;
  
  /**
   * creates a new chunk 
   * @param ID the ID of the chunk, used to calculate global coordinates
   * @param CHUNK_WIDTH the width of a chunk
   * @param CHUNK_HEIGHT the height of a chunk
   * @param seed the seed used to generate the world
   * @param maxCreation this controlls where the generation bias function changes
   */
  public Chunk(int ID, int CHUNK_WIDTH, int CHUNK_HEIGHT, int seed, int maxCreation){
    this.ID=ID;
    this.containedBlocks = new Block [CHUNK_WIDTH][CHUNK_HEIGHT];
    this.seed = seed;
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
    if(containedBlocks[relativeLocation.getX()][relativeLocation.getY()] == null){
      t.singleBlockNoise(null,relativeLocation.getX(),relativeLocation.getY());
      return getBlock(relativeLocation);
    }
    return containedBlocks[relativeLocation.getX()][relativeLocation.getY()];
  }
  /**
   * 
   * @param x the relative location of a block 
   * @param y the relative location of a block 
   * @return the block stored at the given location
   */
  public Block getBlock(int x, int y){
    if(containedBlocks[x][y] == null){
      if (t == null) {
        t = new WorldGenThread(ID, seed, creationHeight, this);
      }
      t.singleBlockNoise(null,x,y);
      try {
        System.out.println("failed get block");
        Thread.sleep(20);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      return getBlock(x, y);
    }
    return containedBlocks[x][y];
  }
  /**
   * resets the chunk by restarting the worldGenThread
   */
  public void generate(){
    if(t == null) t = new WorldGenThread(ID,seed,creationHeight,this);
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
    globalLocation.add(ChunkVector);;
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
  }
}
