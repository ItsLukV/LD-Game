package dk.mtdm.managementSystem.world;

public class ChunkList {
  private static chunkContainer chunks;
  public static void setup(Chunk firstChunk){
    ChunkList.chunks = new chunkContainer(firstChunk);
  }
  public static Chunk getChunk(int ID){
    return chunks.get(ID);
  }
  public static void generate(int ID){
    chunks.generate(ID);
  }
  public static void extendRight(Chunk chunk){
    chunks.extendAfter(chunk);
  }
  public static void extendLeft(Chunk chunk){
    chunks.extendBefore(chunk);
  }
  public static void replace(int ID,Chunk chunk){
    chunks.replace(ID, chunk);
  }

  static class chunkContainer {
    private Chunk containedChunk;
    private chunkContainer after; //higher ID
    private chunkContainer before; //lower ID
    
    public chunkContainer(Chunk chunk){
      containedChunk = chunk;
    }
    public void extendAfter(Chunk chunk){
      if(after != null){
        after.extendAfter(chunk);
        return;
      }
      after = new chunkContainer(chunk);
    }
    public void extendBefore(Chunk chunk){
      if(before != null){
        before.extendAfter(chunk);
        return;
      }
      before = new chunkContainer(chunk);
    }
    public Chunk get(int ID){
      if(ID > 0){
        if(after == null){
          return null;
        }
        return after.get(ID-1);
      } else if(ID < 0){
        if(before == null){
          return null;
        }
        return before.get(ID+1);
      }else{
        return containedChunk;
      }
    }
    public void replace(int ID, Chunk chunk){
      if(ID > 0){
        if(after == null){
          after = new chunkContainer(World.standardChunk(containedChunk.getID()+1));
          after.generator();
          return;
        }
        after.replace(ID-1,chunk);
        return;
      } else if(ID < 0){
        if(before == null){
          before = new chunkContainer(World.standardChunk(containedChunk.getID()-1));
          before.generator();
          return;
        }
        before.replace(ID+1, chunk);
        return;
      }else{
        containedChunk = chunk;
        return;
      }
    }
    public void generate(int ID){
      if(ID > 0){
        if(after == null){
          after = new chunkContainer(World.standardChunk(containedChunk.getID()+1));
          after.generator();
          return;
        }
        after.generate(ID-1);
        return;
      } else if(ID < 0){
        if(before == null){
          before = new chunkContainer(World.standardChunk(containedChunk.getID()-1));
          before.generator();
          return;
        }
        before.generate(ID+1);
        return;
      }else{
        generator();
        return;
      }
    }
    private void generator(){
      containedChunk.generate();
    }
  }
}
