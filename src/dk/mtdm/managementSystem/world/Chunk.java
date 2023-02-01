package dk.mtdm.managementSystem.world;

import dk.mtdm.LDVector;
import dk.mtdm.itemsAndMore.BlockTypes;

public class Chunk {
  final private int ID;
  private BlockTypes[][] containedBlocks;
  final private int seed;
  final private int creationHeight;
  WorldGenThread t;
  
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
    if(!t.atWork){
      try {
        if(t == null) t = new WorldGenThread(ID,seed,creationHeight,this);
        t.start();
      } catch (Exception e) {
        t=null;
      }
    }
  }
}
