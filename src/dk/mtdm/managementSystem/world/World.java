package dk.mtdm.managementSystem.world;

import dk.mtdm.LDVector;
import dk.mtdm.itemsAndMore.Blocks.Block;
import processing.core.PGraphics;
/*
//remember: canvas location = processing
//remember: global location = gameWorld
//remember: relative location = chunk
*/
public class World {
  // private static Chunk[] world;
  // private static Chunk[] worldPositive = new Chunk[0];
  // private static Chunk[] worldNegative = new Chunk[0];
  // private static Chunk worldCentral = null;
  // private static int chunkAxisOffset; // the amount of chunks that are on the left of the global 0, this is needed so that chunk ID can be allowed negative and all chunks can be saved in an array 
  // private static WorldGenThread[] worldGeneraters; 
  final private static int CHUNK_WIDTH = 32;
  private static int HEIGHT = 100;
  private static int seed1 = 3;
  private static int seed2 = 30;
  private static int GeneratorHeight = HEIGHT/3;
  private static float BlockStone = 0.5f;
  private static float BlockAir = 0.3f;
  /**
   * starts the world without a set seed
   * @param width the starting width of the world. in chunks <p>
   * this determines how much of the world will be generated on startup. measured in chunks.
   * @param height the height of the world. <p>
   * the maximum height that can be built in this world, anything placed or destroyed in this can lead to unexpected behaivour. <p>
   * @param maxGeneration determines how high the expected maximum build height for terain generation will be.<p>
   *  THIS LIMIT <b>CAN</b> BE EXCEDED DURING WORLD GEN AND SHOULD <b>NOT</b> BE TAKEN AS AN ABSOLUTE MAX. <p>
   */
  public static void setup(int width,int height, int maxGeneration){
    setup(width, height, randomSeed(),maxGeneration);
  }
  /**
   * starts the world without a set seed
   * @param width the starting width of the world. <p>
   * this determines how much of the world will be generated on startup.
   * @param height the height of the world. <p>
   * the maximum height that can be built in this world, anything placed or destroyed in this can lead to unexpected behaivour. <p>
   * @param seed the seed that will be used to pseudo randomly generate the world. <p>
   * this will be used in a formula to generate pseudo random numbers that in turn get used to generate the world and it's individual chunks.
   * @param maxGeneration determines how high the expected maximum build height for terain generation will be.<p>
   *  THIS LIMIT <b>CAN</b> BE EXCEDED DURING WORLD GEN AND SHOULD <b>NOT</b> BE TAKEN AS AN ABSOLUTE MAX. <p>
   */
  public static void setup(int width,int height,int seed1, int maxGeneration){
    HEIGHT = height;
    World.seed1 = seed1;
    World.GeneratorHeight = maxGeneration;
    ChunkList.setup(standardChunk(0));
    ChunkList.generate(0);
    PerlinNoise.start(World.seed1);
    generateTowardsChunk(width/2);
    generateTowardsChunk(-width/2);
  }
  /**
   * finds a block in the world from a global location
   * @param location the location of a block <p>
   * the location of a block that wants to be accessed in the world, this alows acces to blocks whitout needing to find a specific chunk
   * @return // the enum blocktype coresponding to the block at the given position
   */
  public static Block getBlock(LDVector location){
    
    int chunkID = (location.getX() - location.getX() % CHUNK_WIDTH) / CHUNK_WIDTH;
    int relativeX = location.getX()%CHUNK_WIDTH;
    
    return getChunk(chunkID).getBlock(new LDVector(relativeX, location.getY()));
  }
  /**
   * @return a random int between 'Integer.MAX_VALUE' and 'Integer.MIN_VALUE'
   */
  private static int randomSeed(){
    int seed = (int) Math.floor(1-(Math.random()*2)*Integer.MAX_VALUE);
    // System.out.println(seed);
    return seed;
  }
  /**
   * generates worlds or world parts
   * @param GenerationStart the id of the first chunk in the list of chunks to be generated
   * @param generationWidth the number of chunks to generate
   */
  private static void generateTowardsChunk(int ID){
    ChunkList.generate(ID);
  }
  /**
   * draws entire chunks to the screen
   * @param g
   * @param startChunkID the location of  the first chunk to be drawn
   * @param endChunkID the location of the last chunk to be draw
   */
  public static void show(PGraphics g,int startChunkID, int endChunkID){
    // System.out.println(startChunkID + " " + endChunkID);
    for (int i = startChunkID; i <= endChunkID; i++) {
      try {
        getChunk(i).show(g);
      }catch (NullPointerException e){
        System.out.println("nullpointer chunk: " + i);
        ChunkList.replace(i,standardChunk(i));
        getChunk(i).show(g);
        // world[i+chunkAxisOffset] = new Chunk(i, CHUNK_WIDTH, HEIGHT, seed, GeneratorHeight);
        // world[i+chunkAxisOffset].generate();
      }
    }
  }
  /**
   * draws entire chunks to the screen
   * @param startGlobal the location of  the first block to be drawn
   * @param endGlobal the location of the last block to be draw
   * @param g
   */
  public static void show(int startGlobal, int endGlobal,PGraphics g){
    int start = (int) Math.floor(startGlobal/World.CHUNK_WIDTH);
    int end = (int) Math.floor(endGlobal/World.CHUNK_WIDTH);
    show(g, start, end);
  }
  // getters and setters
  public static int get_CHUNK_WIDTH(){
    return World.CHUNK_WIDTH;
  }
  public static int get_HEIGHT(){
    return World.HEIGHT;
  }
  public static float getBlockStone() {
    return BlockStone;
  }
  public static float getBlockAir() {
    return BlockAir;
  }
  public static int getGeneratorHeight(){
    return GeneratorHeight;
  }
  private static Chunk getChunk(int ID) {
    return ChunkList.getChunk(ID);
  }
  public static Chunk standardChunk(int ID){
    return new Chunk(ID, World.get_CHUNK_WIDTH(), World.get_HEIGHT(), World.getGeneratorHeight());
  }
}
