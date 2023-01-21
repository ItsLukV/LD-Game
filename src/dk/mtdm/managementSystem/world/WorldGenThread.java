package dk.mtdm.managementSystem.world;

import dk.mtdm.managementSystem.Thread;

public class WorldGenThread extends Thread{
  
  @Override
  protected void Run() {
    worldGenerator();
  }
  
  public synchronized void start(int chunkID) {
    super.start();
    
  }
  private void worldGenerator(){
    
  }
}
