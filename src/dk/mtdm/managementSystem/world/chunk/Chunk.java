package dk.mtdm.managementSystem.world.chunk;

import dk.mtdm.exceptions.MissingBlockTypeException;
import dk.mtdm.exceptions.MissingDataException;
import dk.mtdm.itemsAndMore.Blocks.Block;
import dk.mtdm.itemsAndMore.Blocks.BlockPicker;
import dk.mtdm.itemsAndMore.Blocks.BlockTypes;
import dk.mtdm.location.LocationTypes;
import dk.mtdm.location.WWL;
import dk.mtdm.location.WorldWideLocation;
import dk.mtdm.managementSystem.world.World;
import processing.core.PGraphics;
public class Chunk {
  final private int ID;
  private Block[][] containedBlocks;
  final private int creationHeight;
  private int chunkError = 0;
  public WorldGenThread t;
  
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
  }
  public Chunk(Block[][] chunk,int ID,int creationHeight){
    containedBlocks = chunk;
    this.ID = ID;
    this.creationHeight = creationHeight;
  }
  /**
   * @return the ID of this chunk
   */
  public int getID(){
    return ID;
  }
  public void joinGen(){
    try {
      t.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
  /**
   * @param relativeLocation the relative location of a block 
   * @return the block stored at the given location
   */
  public Block getBlock(WWL Location){
    try {
      return getBlock(Location.getRelative().getX(), Location.getRelative().getY());
    } catch (MissingDataException e) {
      e.printStackTrace();
      return getBlock(0, 0);
    }
  }
  /**
   * 
   * @param x the relative location of a block 
   * @param y the relative location of a block 
   * @return the block stored at the given location
   */
  public Block getBlock(int x, int y){
    if(containedBlocks[x][y] != null){
      containedBlocks[x][y].setChunkID(ID);
      return containedBlocks[x][y];
    }
    
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
    if(containedBlocks[x][y] != null){
      containedBlocks[x][y].setChunkID(ID);
      return containedBlocks[x][y];
    } 
    
    WWL tempBlockVector = WorldWideLocation.create(x, y, LocationTypes.relative);
    tempBlockVector.setChunkID(ID);
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
  public void setBlock(WWL location,BlockTypes block){
    WWL Location = location.copy();
    Location.setChunkID(ID);
    location.setChunkID(ID);
    try {
      try {
        containedBlocks[location.getRelative().getX()][location.getRelative().getY()] = BlockPicker.picker(block,Location);
      } catch (MissingDataException e) {
        e.printStackTrace();
      }
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

  public void border(PGraphics g){
    g.push();
    g.noFill();
    g.stroke(255,0,0);
    g.rect(ID*World.get_CHUNK_WIDTH()*Block.getWidth(),0,World.get_CHUNK_WIDTH()*Block.getWidth(),-World.get_HEIGHT()*Block.getHeight());
    g.pop();
  }
  public String getState() {
    try {
      t.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    String out = "";
    for (int x = 0; x < containedBlocks.length; x++) {
      for (int y = 0; y < containedBlocks[x].length; y++) {
        out += containedBlocks[x][y].getState() + ";";
      }
      out += "\n";
    }
    out+= ID + "," + creationHeight;
    return out;
  } 

}
