package dk.mtdm.managementSystem.world;

import dk.mtdm.LDVector;
import dk.mtdm.itemsAndMore.Block;
import dk.mtdm.itemsAndMore.BlockTypes;
import processing.core.PGraphics;

public class Chunk {
  final private int ID;
  private Block[][] containedBlocks;
  final private int seed;
  final private int creationHeight;
  WorldGenThread t;
  
  public Chunk(int ID, int CHUNK_WIDTH, int CHUNK_HEIGHT, int seed, int maxCreation){
    this.ID=ID;
    this.containedBlocks = new Block [CHUNK_WIDTH][CHUNK_HEIGHT];
    this.seed = seed;
    this.creationHeight = maxCreation;
  }
  public int getID(){
    return ID;
  }
  public Block getBlock(LDVector relativeLocation){
    if(containedBlocks[relativeLocation.getX()][relativeLocation.getY()] == null){
      t.singleBlockNoise(null,relativeLocation.getX(),relativeLocation.getY());
    }
    return containedBlocks[relativeLocation.getX()][relativeLocation.getY()];
  }
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
  public void setBlock(LDVector location,BlockTypes block){
    containedBlocks[location.getX()][location.getY()] = new Block(location, block);
  }
  public Block[][] getAllBlocks(){
    return containedBlocks;
  }
  public void show(PGraphics g){
    for (Block[] blocks : containedBlocks) {
      for (Block block : blocks) {
        block.show(g);
      }
    }
  }
}
