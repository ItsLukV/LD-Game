package dk.mtdm.managementSystem.world;

import dk.mtdm.exceptions.MissingBlockTypeException;
import dk.mtdm.exceptions.MissingDataException;
import dk.mtdm.exceptions.MissingTextureException;
import dk.mtdm.itemsAndMore.Blocks.Block;
import dk.mtdm.itemsAndMore.Blocks.BlockPicker;
import dk.mtdm.itemsAndMore.texureFiles.BlockTextures;
import dk.mtdm.itemsAndMore.Blocks.BlockTypes;
import dk.mtdm.location.LDVector;
import dk.mtdm.location.LocationTypes;
import dk.mtdm.location.WorldWideLocation;
import processing.core.PGraphics;
public class Chunk {
  final private int ID;
  private Block[][] containedBlocks;
  final private int creationHeight;
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
  public Block getBlock(WorldWideLocation Location){
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
    
    WorldWideLocation tempBlockVector = WorldWideLocation.create(x, y, LocationTypes.relative);
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
  public void setBlock(WorldWideLocation location,BlockTypes block){
    WorldWideLocation Location = location.copy();
    Location.setChunkID(ID);
    location.setChunkID(ID);
    try {
      try {
        containedBlocks[location.getRelative().getX()][location.getRelative().getY()] = BlockPicker.picker(block,Location);
      } catch (MissingDataException e) {
        // TODO Auto-generated catch block
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
