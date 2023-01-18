package dk.mtdm.ThreadSystem.WorldThread;

import java.util.ArrayList;

import dk.mtdm.Point;
import dk.mtdm.ThreadSystem.Thread;
import dk.mtdm.ThreadSystem.WorldThread.WorldChangeType;
import dk.mtdm.itemsAndMore.BlockTypes;

public class WorldThread extends Thread {
  private static ArrayList<WorldChange> changeQueue = new ArrayList<WorldChange>();
  private static BlockTypes[][] world;
  
  public synchronized boolean addJob(WorldChange job){
    changeQueue.add(job);
    return true;
  }
  
  @Override
  protected void Start() {
    // TODO Auto-generated method stub
    
  }
  
  @Override
  protected void Run() {
    queueCheck();
  }
  
  private void queueCheck(){
    WorldChange currentChagnge = changeQueue.get(changeQueue.size()-1);
    switch (currentChagnge.getAction()) {
      
      case destroyBlock:
        
        break;
      case placeBlock:
        
        break;
      case requestWorldGen:
        
        break;
      case physicsRequest:
        
        break;
        
      default:
        break;
    }
  }
  
  private void WorldGen(int width,int height, Point StartingPoint){
  
  }
  private void WorldGen(Point start){
    
  }
}
