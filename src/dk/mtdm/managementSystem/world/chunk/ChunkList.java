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
        chunks.add(0, new chunkContainer(World.standardChunk(0), 0));
        // try {
        //   chunks.get(0).generate(0);
        //   chunks.get(0).get(0).t.join(100);//not needed but prevents error from chunk gen delays
        // }catch(InterruptedException empty) {}
      }
      while(ID+dimensionOffset >= chunks.size()){
        PseudeRandom.update(chunks.size()-dimensionOffset);
        chunks.add(new chunkContainer(World.standardChunk(0), 0));
      //   try {
      //     // chunks.get(chunks.size()-1).generate(0);
      //     // chunks.get(chunks.size()-1).get(0).t.join(100);//not needed but prevents error from chunk gen delays
      //   }catch(InterruptedException empty) {}
      }
      try {
        chunks.get(ID+dimensionOffset);
        chunks.get(ID+dimensionOffset).generate(0);
        dimensionID = ID;
        LOCK = false;
        chunks.get(ID+dimensionOffset).generate(Player.getCanvas().getX() / World.get_CHUNK_WIDTH() / Block.getWidth());
        chunks.get(ID+dimensionOffset).get((Player.getCanvas().getX() / World.get_CHUNK_WIDTH() / Block.getWidth())).t.join();
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
  public static String[][] getState() {
    String[][] out = new String[chunks.size()+1][];
    for (int i = 0; i < chunks.size(); i++) {
      int[] edges = findEdges(i);
      out[i] = new String[edges[1]-edges[0]];
      for (int j = edges[0]; j < edges[1]; j++) {
        out[i][j-edges[0]] = chunks.get(i).get(j).getState() + "\n";
      }
    }
    out[chunks.size()] = new String[1];
    out[chunks.size()][0] = "" + (-dimensionOffset);
    return out;
  }
  private static int[] findEdges(int i) {
    int ID = chunks.get(i).getID();
    boolean up = ID > 0;
    while(true){
      if(chunks.get(i).get(ID) != null){
        if(up){
          ID++;
        }else{
          ID--;
        }
      }else{
        break;
      }
    }
    int point = ID;
    ID = 0;
    while(true){
      if(chunks.get(i).get(ID) != null){
        if(!up){
          ID++;
        }else{
          ID--;
        }
      }else{
        break;
      }
    }
    if(ID <= point){
      int[] out = {ID+1,point-1};
      return out;
    }
    int[] out = {point+1,ID-1};
      return out;
  }
}
