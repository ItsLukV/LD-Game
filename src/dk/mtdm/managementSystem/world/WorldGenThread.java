package dk.mtdm.managementSystem.world;


import dk.mtdm.LDVector;
import dk.mtdm.itemsAndMore.BlockTypes;
import dk.mtdm.managementSystem.Thread;
import processing.core.PApplet;

public class WorldGenThread extends Thread{
  
  int seed;
  int ID;
  float[][] noise;
  int maxcreation;
  Chunk parent;



  public WorldGenThread(int ID, int seed,int creationHeight,Chunk attachedChunk){
    this.seed = seed; 
    this.ID = ID;
    this.maxcreation = creationHeight;
    this.noise = new float[World.get_CHUNK_WIDTH()][World.get_HEIGHT()];
    this.parent= attachedChunk;
  }
  public synchronized void changeID(int ID){
    this.ID = ID;
  }

  @Override
  public void Run() {
    worldGenerator();
  }
  
  public synchronized void start() {
    super.start();
  }
  private void worldGenerator(){
    if(noise == null) noiseGenerator();

    for (int x = 0; x < noise.length; x++) {
      for (int y = 0; y < noise[x].length; y++) {
        ChooseBlock(x,y);
      }
    }
  }
  public void noiseGenerator(){
    PApplet p = new PApplet();
    p.noiseSeed(-1519604120685001364l);
    
    //inside noise
    for (int x = 0; x < World.get_CHUNK_WIDTH(); x++) {
      for (int y = 0; y < World.get_HEIGHT(); y++) {
        float tempNoise =p.noise(x+ID*World.get_CHUNK_WIDTH(), y, seed);
        noise[x][y] = tempNoise;
        noise[x][y] += 1f/Math.ceil(1+(float)y/2f);
        if(y > maxcreation) noise[x][y] -= (1-1f/(1+y-maxcreation))/5f;
        System.out.println(y + ": " + noise[x][y]);
      }
    }
  }
  private void ChooseBlock(int x, int y) {    
    PApplet p = new PApplet();
    p.noiseSeed(-1519604120685001364l);
    //outside noise
    float[][] outNoise = new float[2][World.get_HEIGHT()];
    for (int Y = 0; Y < World.get_HEIGHT(); Y++) {
      outNoise[0][Y] = p.noise(ID*World.get_CHUNK_WIDTH()-1, Y, seed);
      outNoise[1][Y] = p.noise((ID+1)*World.get_CHUNK_WIDTH(), Y, seed);
    }

    
    if(noise[x][y] < World.getBlockAir()) {
      parent.setBlock(new LDVector(x, y), BlockTypes.air);
      return;
    }
    if(noise[x][y] > World.getBlockStone()) {
      parent.setBlock(new LDVector(x, y), BlockTypes.stone);
      return;
    }
    
    if(x == 0){
      if (noise[x][y-1] < World.getBlockAir() &&
          noise[x][y-1] < World.getBlockAir() &&
          noise[x+1][y] < World.getBlockAir() &&
          outNoise[0][y]< World.getBlockAir()){
            
            parent.setBlock(new LDVector(x, y), BlockTypes.grass);
            return;
          }
    }
    if(x == World.get_CHUNK_WIDTH()-1){
      if (noise[x][y-1] < World.getBlockAir() &&
          noise[x][y-1] < World.getBlockAir() &&
          noise[x-1][y] < World.getBlockAir() &&
          outNoise[1][y]< World.getBlockAir()){
            
            parent.setBlock(new LDVector(x, y), BlockTypes.grass);
            return;
          }
    }
    parent.setBlock(new LDVector(x, y), BlockTypes.dirt);
    return;
  }
}
