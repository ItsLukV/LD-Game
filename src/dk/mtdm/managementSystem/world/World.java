package dk.mtdm.managementSystem.world;

import dk.mtdm.LDVector;
import dk.mtdm.itemsAndMore.BlockTypes;
/*
//TODO canvas location = processing
//TODO global location = gameWorld
//TODO relative location = chunk
*/
public class World {
  private static Chunk[] world;
  private static int chunkAxisOffset; // the amount of chunks that are on the left of the global 0, this is needed so that chunk ID can be allowed negative and all chunks can be saved in an array 
  private static WorldGenThread[] worldGeneraters; 
  final private static int CHUNK_WIDTH = 32;
  private static int HEIGHT;
  private static int seed;
  private static int GeneratorHeight;
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
  public static BlockTypes getBlock(LDVector location){
    
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
    if (World.chunkAxisOffset < GenerationStart){
      World.world = new Chunk[World.world.length+(GenerationStart-chunkAxisOffset)];
      chunkAxisOffset = GenerationStart;
    }
    for (int i = GenerationStart; i < Math.abs(generationWidth); i++){
      World.world[i-chunkAxisOffset] = new Chunk(i, CHUNK_WIDTH, HEIGHT, World.seed,GeneratorHeight);
      World.world[i-chunkAxisOffset].generate();
    }
  }
  public static int get_CHUNK_WIDTH(){
    return World.CHUNK_WIDTH;
  }
  public static int get_HEIGHT(){
    return World.HEIGHT;
  }
}
