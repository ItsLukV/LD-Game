package dk.mtdm.managementSystem.world;

import dk.mtdm.LDVector;
import dk.mtdm.itemsAndMore.BlockTypes;

public class Chunk {
  final private int ID;
  private BlockTypes[][] containedBlocks;
  final private int seed;
  
  public Chunk(int ID, int CHUNK_WIDTH, int CHUNK_HEIGHT, int seed){
    this.ID=ID;
    this.containedBlocks = new BlockTypes [CHUNK_WIDTH][CHUNK_HEIGHT];
    this.seed = seed;
  }
  public int getID(){
    return ID;
  }
  public BlockTypes getBlock(LDVector relativeLocation){
    return containedBlocks[relativeLocation.getX()][relativeLocation.getY()];
  }
  public void generate(){
    return;
  }
  private int randomCalc(int x,int y){
    return 0;
  }
}
