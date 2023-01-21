package dk.mtdm.managementSystem.world;

import dk.mtdm.LDVector;
import dk.mtdm.itemsAndMore.BlockTypes;
import processing.core.PApplet;

public class Chunk {
  final private int ID;
  private BlockTypes[][] containedBlocks;
  final private int seed;
  final private int creationHeight;
  
  public Chunk(int ID, int CHUNK_WIDTH, int CHUNK_HEIGHT, int seed, int maxCreation){
    this.ID=ID;
    this.containedBlocks = new BlockTypes [CHUNK_WIDTH][CHUNK_HEIGHT];
    this.seed = seed;
    this.creationHeight = maxCreation;
  }
  public int getID(){
    return ID;
  }
  public BlockTypes getBlock(LDVector relativeLocation){
    return containedBlocks[relativeLocation.getX()][relativeLocation.getY()];
  }
  public void generate(){
    WorldGenThread t = new WorldGenThread(ID,seed);
    t.start();
  }
}
