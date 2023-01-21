package dk.mtdm.managementSystem.world;

import java.util.Vector;


import dk.mtdm.LDVector;
import dk.mtdm.managementSystem.Thread;

public class WorldGenThread extends Thread{
  
  int seed;
  int ID;
  float[][] noise;

  int[] Corner = {ID,ID+1,};


  public WorldGenThread(int ID, int seed){
    this.seed = seed; 
    this.ID = ID;
  }
  public synchronized void changeID(int ID){
    this.ID = ID;
  }

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