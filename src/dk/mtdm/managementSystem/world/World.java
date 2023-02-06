package dk.mtdm.managementSystem.world;

import dk.mtdm.LDVector;
import dk.mtdm.itemsAndMore.Block;
import processing.core.PGraphics;
/*
//remember: canvas location = processing
//remember: global location = gameWorld
//remember: relative location = chunk
*/
public class World {
  private static Chunk[] world;
  private static int chunkAxisOffset; // the amount of chunks that are on the left of the global 0, this is needed so that chunk ID can be allowed negative and all chunks can be saved in an array 
  // private static WorldGenThread[] worldGeneraters; 
  final private static int CHUNK_WIDTH = 32;
  private static int HEIGHT = 50;
  private static int seed = 3;
  private static int GeneratorHeight = HEIGHT/2;
  private static float BlockStone = 0.5f;
  private static float BlockAir = 0.2f;
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
  public static void setup(int width,int height,int seed, int maxGeneration){
    HEIGHT = height;
    World.seed = seed;
    World.GeneratorHeight = maxGeneration;
    generateWorld(-width/2, width);
  }
  /**
   * finds a block in the world from a global location
   * @param location the location of a block <p>
   * the location of a block that wants to be accessed in the world, this alows acces to blocks whitout needing to find a specific chunk
   * @return // the enum blocktype coresponding to the block at the given position
   */
  public static Block getBlock(LDVector location){
    
    int chunkID = (location.getX() - location.getX() % CHUNK_WIDTH) / CHUNK_WIDTH - chunkAxisOffset;
    int relativeX = location.getX()%CHUNK_WIDTH;
    
    return world[chunkID].getBlock(new LDVector(relativeX, location.getY()));
  }
  /**
   * @return a random int between 'Integer.MAX_VALUE' and 'Integer.MIN_VALUE'
   */
  private static int randomSeed(){
    return (int) Math.floor(1-(Math.random()*2)*Integer.MAX_VALUE);
  }
  /**
   * generates worlds or world parts
   * @param GenerationStart the id of the first chunk in the list of chunks to be generated
   * @param generationWidth the number of chunks to generate
   */
  private static void generateWorld(int GenerationStart,int generationWidth){
    if(world == null){
      world = new Chunk[0];
    }
    boolean change = false;
    
    int frontChange = 0;
    if(GenerationStart < -World.chunkAxisOffset){
      frontChange = -GenerationStart - World.chunkAxisOffset;
      change = true;
    }
    int backChange = 0;
    if(GenerationStart + generationWidth > World.world.length-World.chunkAxisOffset){
      backChange = World.world.length-World.chunkAxisOffset - GenerationStart + generationWidth;
      change = true;
    }
    if(change){
      int newChunkOffset = World.chunkAxisOffset;
      if(frontChange > 0){
        newChunkOffset = World.chunkAxisOffset + frontChange;
      }
      Chunk[] newChunks = new Chunk[World.chunkAxisOffset + frontChange + World.world.length + backChange];
      for (int chunkPlace = -(World.chunkAxisOffset + frontChange); chunkPlace < World.world.length + backChange; chunkPlace++) {
        try {
          newChunks[chunkPlace-newChunkOffset] = World.world[chunkPlace+World.chunkAxisOffset];
        } catch (Exception e) {
          newChunks[chunkPlace+newChunkOffset] = new Chunk(chunkPlace, World.CHUNK_WIDTH, HEIGHT, seed, GeneratorHeight);
        }
      }
      World.chunkAxisOffset = newChunkOffset;
      World.world = newChunks;
    }
    
    for (int i = GenerationStart; i < Math.abs(generationWidth); i++){
      World.world[i+chunkAxisOffset] = new Chunk(i, CHUNK_WIDTH, HEIGHT, World.seed,GeneratorHeight);
      World.world[i+chunkAxisOffset].generate();
    }
  }
  /**
   * draws entire chunks to the screen
   * @param g
   * @param startChunkID the location of  the first chunk to be drawn
   * @param endChunkID the location of the last chunk to be draw
   */
  public static void show(PGraphics g,int startChunkID, int endChunkID){
    for (int i = startChunkID; i <= endChunkID; i++) {
      World.world[i+chunkAxisOffset].show(g);
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
}
