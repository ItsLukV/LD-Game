package dk.mtdm.managementSystem.world;


import dk.mtdm.LDVector;
import dk.mtdm.itemsAndMore.Blocks.BlockTypes;
import dk.mtdm.managementSystem.Thread;
import processing.core.PApplet;
/**
 * @author @dendersen
 */
public class WorldGenThread extends Thread{
  
  private int seed;
  private int ID;
  private float[][] noise;
  private int maxcreation;
  private Chunk parent;
  private static PApplet p = new PApplet();


  /**
 * generates a thread with the purpose of generating all blocks present in a chunk without lagging main thread
 * @param ID the id of the chunk, this is used to find the global coordinates of this chunk
 * @param seed the seed for the world, this is used as a seed in the noise function
 * @param creationHeight this controlls where the generation bias function changes
 * @param attachedChunk the chunk all the generated blocks should be saved to
 */
  public WorldGenThread(int ID,int creationHeight,Chunk attachedChunk){
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
  protected void Run() {
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
    
    //inside noise
    for (int x = 0; x < World.get_CHUNK_WIDTH(); x++) {
      for (int y = 0; y < World.get_HEIGHT(); y++) {
        noise[x][y] = singleBlockNoise(x, y);
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
  public float singleBlockNoise(int x, int y) {
    
    float tempNoise = noisePoint(x+(ID*World.get_CHUNK_WIDTH()), y);
    tempNoise += 1f/Math.ceil(1+(float)y/2f);
    if(y > maxcreation) tempNoise -= (1-1f/(1+y-maxcreation))/3f;
    return tempNoise;
  }
  /**
   * chooses and sets the block from noise
   * @param x the relative x coordinate of the block
   * @param y the relative y coordinate of the block
   */
  private void ChooseBlock(int x, int y) {    
    ChooseBlock(noise[x][y],x, y);
  }

  public void ChooseBlock(float noise, int x, int y){
    
    //outside noise
    float[][] outNoise = new float[2][World.get_HEIGHT()];
    for (int Y = 0; Y < World.get_HEIGHT(); Y++) {
      outNoise[0][Y] = noisePoint(ID*World.get_CHUNK_WIDTH()-1, Y);
      outNoise[1][Y] = noisePoint((ID+1)*World.get_CHUNK_WIDTH(), Y);
    }

    
    if(noise < World.getBlockAir()) {
      parent.setBlock(new LDVector(x, y), BlockTypes.air);
      return;
    }
    if(noise > World.getBlockStone()*3) {
      parent.setBlock(new LDVector(x, y), BlockTypes.bedrock);
      return;
    }
    if(noise > World.getBlockStone()) {
      parent.setBlock(new LDVector(x, y), BlockTypes.stone);
      return;
    }
    
    if(x == 0){
      try {
        if (this.noise[x][y-1] < World.getBlockAir() ||
            this.noise[x][y+1] < World.getBlockAir() ||
            this.noise[x+1][y] < World.getBlockAir() ||
            outNoise[0][y]< World.getBlockAir()){
              parent.setBlock(new LDVector(x, y), BlockTypes.grass);
              return;
            }
      } catch (Exception e) {
        // System.out.println("edge");
      }
    }
    if(x == World.get_CHUNK_WIDTH()-1){
      try {
        if (this.noise[x][y-1] < World.getBlockAir() ||
            this.noise[x][y-1] < World.getBlockAir() ||
            this.noise[x-1][y] < World.getBlockAir() ||
            outNoise[1][y]< World.getBlockAir()){
              
              parent.setBlock(new LDVector(x, y), BlockTypes.grass);
              return;
            }
        
      } catch (Exception e) {
        // System.out.println("edge");
      }
    }
    try {
      if(this.noise[x][y-1] < World.getBlockAir() || this.noise[x][y-1] < World.getBlockAir() || this.noise[x-1][y] < World.getBlockAir() || this.noise[x+1][y]< World.getBlockAir()){
        parent.setBlock(new LDVector(x, y), BlockTypes.grass);
        return;
      }
    } catch (Exception e) {
      // System.out.println("edge");
    }
    parent.setBlock(new LDVector(x, y), BlockTypes.dirt);
    return;
  }
  private float noisePoint(int globalX, int globalY){
    final float discrep = 16f;
    p.noiseSeed(seed);
    return PerlinNoise.getNoise((float)globalX/discrep, (float)globalY/discrep);
  }
}