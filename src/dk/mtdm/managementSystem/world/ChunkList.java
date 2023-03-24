package dk.mtdm.managementSystem.world;

import java.util.LinkedList;

public class ChunkList {
  private static LinkedList<chunkContainer> chunks;
  private static int dimensionID; 
  private static int dimensionOffset = 0;
  public static void setup(Chunk firstChunk){
    dimensionID = 0;
    ChunkList.chunks = new LinkedList<chunkContainer>();
    ChunkList.chunks.add(dimensionID,new chunkContainer(firstChunk,0));
  }
  public static Chunk getChunk(int ID){
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
    chunks.get(dimensionID+dimensionOffset).replace(ID, chunk);
  }
  public static void setDimensionID(int ID){
    while(true){
      while(dimensionOffset + ID < 0){
        dimensionOffset++;
        PseudeRandom.update(-dimensionOffset);
        ChunkList.chunks.add(0, new chunkContainer(World.standardChunk(0), 0));
        try {
          ChunkList.chunks.get(0).get(0).t.join(100);//not needed but prevents error from chunk gen delays
        }catch(InterruptedException empty) {}
      }
      while(ID+dimensionOffset >= ChunkList.chunks.size()){
        PseudeRandom.update(ChunkList.chunks.size()-dimensionOffset);
        ChunkList.chunks.add(0, new chunkContainer(World.standardChunk(0), 0));
        try {
          ChunkList.chunks.get(ChunkList.chunks.size()-1).get(0).t.join(100);//not needed but prevents error from chunk gen delays
        }catch(InterruptedException empty) {}
      }
      try {
        ChunkList.chunks.get(ID+dimensionOffset);
        ChunkList.dimensionID = ID;
        return;
      }catch(IndexOutOfBoundsException e){
        e.printStackTrace();
      }
    }
  }
  public static void setChunkContainerStart(chunkContainer start){
    chunks.set(dimensionID,start);
  }
  public static int getDimensionID(){
    return dimensionID;
  }

  static class chunkContainer {
    private Chunk containedChunk;
    private chunkContainer after; //higher ID
    private chunkContainer before; //lower ID
    private int ID; 
    
    public chunkContainer(Chunk chunk, int ID){
      containedChunk = chunk;
      this.ID = ID;
    }
    
    public void extendAfter(Chunk chunk){
      if(after != null){
        after.extendAfter(chunk);
        return;
      }
      after = new chunkContainer(chunk,this.ID+1);
      after.setbefore(this);
    }
    public void extendBefore(Chunk chunk){
      if(before != null){
        before.extendAfter(chunk);
        return;
      }
      before = new chunkContainer(chunk,this.ID-1);
      before.setAfter(this);
    }
    public Chunk get(int ID){
      if(ID > this.ID){
        if(after == null){
          return null;
        }
        return after.get(ID);
      } else if(ID < this.ID){
        if(before == null){
          return null;
        }
        return before.get(ID);
      }else{
        ChunkList.setChunkContainerStart(this);
        return containedChunk;
      }
    }
    public void replace(int ID, Chunk chunk){
      if(ID > this.ID){
        if(after == null){
          after = new chunkContainer(World.standardChunk(containedChunk.getID()+1),ID+1);
          after.generator();
          after.setbefore(this);
          return;
        }
        after.replace(ID,chunk);
        return;
      } else if(ID < this.ID){
        if(before == null){
          before = new chunkContainer(World.standardChunk(containedChunk.getID()-1),ID-1);
          before.generator();
          before.setAfter(this);
          return;
        }
        before.replace(ID, chunk);
        return;
      }else{
        containedChunk = chunk;
        return;
      }
    }
    public void generate(int ID){
      if(ID > this.ID){
        if(after == null){
          after = new chunkContainer(World.standardChunk(containedChunk.getID()+1),this.ID+1);
          after.generator();
          after.setbefore(this);
          return;
        }
        after.generate(ID);
        return;
      } else if(ID < this.ID){
        if(before == null){
          before = new chunkContainer(World.standardChunk(containedChunk.getID()-1),this.ID-1);
          before.generator();
          before.setAfter(this);
          return;
        }
        before.generate(ID);
        return;
      }else{
        generator();
        return;
      }
    }
    private void generator(){
      containedChunk.generate();
    }
    public void setbefore(chunkContainer chunk){
      before = chunk;
    }
    public void setAfter(chunkContainer chunk){
      after = chunk;
    }
  }
}
