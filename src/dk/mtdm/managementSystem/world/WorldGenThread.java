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


/**
 * generates a thread with the purpose of generating all blocks present in a chunk without lagging main thread
 * @param ID the id of the chunk, this is used to find the global coordinates of this chunk
 * @param seed the seed for the world, this is used as a seed in the noise function
 * @param creationHeight this controlls where the generation bias function changes
 * @param attachedChunk the chunk all the generated blocks should be saved to
 */
  public WorldGenThread(int ID, int seed,int creationHeight,Chunk attachedChunk){
    this.seed = seed; 
    this.ID = ID;
    this.maxcreation = creationHeight;
    this.noise = new float[World.get_CHUNK_WIDTH()][World.get_HEIGHT()];
    this.parent= attachedChunk;
  }
  /**
   * allows one to change the location of the chunk
   * @param ID the ID of the chunk
   * @param attachedChunk the chunk that 
   */
  public synchronized void changeID(int ID,Chunk attachedChunk){
    this.ID = ID;
    this.parent = attachedChunk;
  }
  /**
   * starts the world generator
   */
  @Override
  public void Run() {
    worldGenerator();
  }
  /**
   * starts the thread
   */
  public synchronized void start() {
    super.start();
  }
  /**
   * generates the world 
   */
  private void worldGenerator(){
    noiseGenerator();

    for (int x = 0; x < noise.length; x++) {
      for (int y = 0; y < noise[x].length; y++) {
        ChooseBlock(x,y);
      }
    }
  }
  /**
   * generates the noise for all  coordinates in the chunk
   */
  public void noiseGenerator(){
    PApplet p = new PApplet();
    p.noiseSeed(-1519604120685001364l);
    
    //inside noise
    for (int x = 0; x < World.get_CHUNK_WIDTH(); x++) {
      for (int y = 0; y < World.get_HEIGHT(); y++) {
        noise[x][y] = singleBlockNoise(p, x, y);
      }
    }
  }
  /**
   * generates the noise of a singel block in the  chunk
   * @param p a Papplet used for storing/generating noise
   * @param x the relative x coordinate of the block
   * @param y the relative y coordinate of the block
   * @return the noise of this block
   */
  public synchronized float singleBlockNoise(PApplet p, int x, int y) {
    if(p == null){
      p = new PApplet();
      p.noiseSeed(-1519604120685001364l);
    }
    
    float tempNoise =p.noise(x+ID*World.get_CHUNK_WIDTH(), y, seed);
    tempNoise += 1f/Math.ceil(1+(float)y/2f);
    if(y > maxcreation) tempNoise -= (1-1f/(1+y-maxcreation))/5f;
    return tempNoise;
  }
  /**
   * chooses and sets the block from noise
   * @param x the relative x coordinate of the block
   * @param y the relative y coordinate of the block
   */
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
