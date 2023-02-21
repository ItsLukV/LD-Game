package dk.mtdm.managementSystem.world;


public class PerlinNoise {
  private  static int seed = 1;
  /**
   * starts the noise generator
   * @param seed
   * @param seed2
   */
  public static void start(int seed){
    PerlinNoise.seed = seed;
  }
  public synchronized static float getNoise(float x, float y){
    
    float BotLeft = pseudoRandom((int) Math.floor(x), (int) Math.floor(y));
    float BotRight = pseudoRandom((int) Math.ceil(x), (int) Math.floor(y));
    float TopLeft = pseudoRandom((int) Math.floor(x), (int) Math.ceil(y));
    float TopRight = pseudoRandom((int) Math.ceil(x), (int) Math.ceil(y));
    
    float Top = TopLeft + ((TopRight-TopLeft) * (x - (float) Math.floor(x)));
    float Bot = BotLeft + ((BotRight-BotLeft) * (x - (float) Math.floor(x)));
    return Bot + ((Top-Bot) * (y -  (float) Math.floor(y)));
  }
  public synchronized static float pseudoRandom(int x, int y){
    return PseudeRandom.attractor_3Cell_CNN(x, y, seed);
    // return PseudeRandom.Float(x, y,seed);
  }
}
