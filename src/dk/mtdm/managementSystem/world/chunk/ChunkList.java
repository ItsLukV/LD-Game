package dk.mtdm.managementSystem.world.chunk;

import java.util.LinkedList;

import dk.mtdm.Sketch;
import dk.mtdm.itemsAndMore.Blocks.Block;
import dk.mtdm.managementSystem.Entitys.Player;
import dk.mtdm.managementSystem.world.PseudeRandom;
import dk.mtdm.managementSystem.world.World;

public class ChunkList {
  private static LinkedList<chunkContainer> chunks;
  private static int dimensionID; 
  private static int dimensionOffset = 0;
  private static boolean LOCK;
  public static void setup(Chunk firstChunk){
    dimensionID = 0;
    ChunkList.chunks = new LinkedList<chunkContainer>();
    ChunkList.chunks.add(dimensionID,new chunkContainer(firstChunk,0));
  }
  public static Chunk getChunk(int ID){
    if(LOCK){
      return World.standardChunk(ID);
    }
    return chunks.get(dimensionID+dimensionOffset).get(ID);
  }
  public static void generate(int ID){
    chunks.get(dimensionID+dimensionOffset).generate(ID);
  }
  public static void extendRight(Chunk chunk){
    chunks.get(dimensionID+dimensionOffset).extendAfter(chunk);
  }
  public static void extendLeft(Chunk chunk){
    chunks.get(dimensionID+dimensionOffset).extendBefore(chunk);
  }
  public static void replace(int ID,Chunk chunk){
    if(LOCK){
      return;
    }
    chunks.get(dimensionID+dimensionOffset).replace(ID, chunk);
  }
  public static void setDimensionID(int ID){
    LOCK = true;
    while(true){
      while(dimensionOffset + ID < 0){
        dimensionOffset++;
        PseudeRandom.update(-dimensionOffset);
        ChunkList.chunks.add(0, new chunkContainer(World.standardChunk(0), 0));
        try {
          ChunkList.chunks.get(0).generate(0);
          ChunkList.chunks.get(0).get(0).t.join(100);//not needed but prevents error from chunk gen delays
        }catch(InterruptedException empty) {}
      }
      while(ID+dimensionOffset >= ChunkList.chunks.size()){
        PseudeRandom.update(ChunkList.chunks.size()-dimensionOffset);
        ChunkList.chunks.add(new chunkContainer(World.standardChunk(0), 0));
        try {
          ChunkList.chunks.get(ChunkList.chunks.size()-1).generate(0);
          ChunkList.chunks.get(ChunkList.chunks.size()-1).get(0).t.join(100);//not needed but prevents error from chunk gen delays
        }catch(InterruptedException empty) {}
      }
      try {
        ChunkList.chunks.get(ID+dimensionOffset);
        ChunkList.chunks.get(ID+dimensionOffset).generate(0);
        ChunkList.dimensionID = ID;
        LOCK = false;
        ChunkList.chunks.get(ID+dimensionOffset).generate(Sketch.player.getCanvas().getX() / World.get_CHUNK_WIDTH() / Block.getWidth());
        ChunkList.chunks.get(ID+dimensionOffset).get((Sketch.player.getCanvas().getX() / World.get_CHUNK_WIDTH() / Block.getWidth())).t.join();
        return;
      }catch(IndexOutOfBoundsException e){
        e.printStackTrace();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
  public static void setChunkContainerStart(chunkContainer start){
    if(LOCK){
      return;
    }
    chunks.set(dimensionID+dimensionOffset,start);
  }
  public static int getDimensionID(){
    return dimensionID;
  }
}
