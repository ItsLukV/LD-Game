package dk.mtdm.managementSystem.world.chunk;

import dk.mtdm.managementSystem.world.World;

class chunkContainer {
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
        after.generate(ID);
        return;
      }
      after.generate(ID);
      return;
    } else if(ID < this.ID){
      if(before == null){
        before = new chunkContainer(World.standardChunk(containedChunk.getID()-1),this.ID-1);
        before.generator();
        before.setAfter(this);
      before.generate(ID);
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
  public int getID(){
    return ID;
  }
}
