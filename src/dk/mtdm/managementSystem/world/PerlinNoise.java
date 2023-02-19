package dk.mtdm.managementSystem.world;

import dk.mtdm.LDVector;

public class PerlinNoise {
  private  static int seed;
  private  static int seed2;
  /**
   * starts the noise generator
   * @param seed
   * @param seed2
   */
  public static void start(int seed,int seed2){
    PerlinNoise.seed = seed;
    PerlinNoise.seed2= seed2;
  }
  public static float getNoise(float x, float y){
    
    float BotLeft = (float)(PerlinNoise.pseudoRandom((int)Math.pow(Math.floor(x), Math.floor(y))));
    float TopRight = (float)(PerlinNoise.pseudoRandom((int)Math.pow(Math.ceil(x), Math.ceil(y))));
    float TopLeft = (float)(PerlinNoise.pseudoRandom((int)Math.pow(Math.floor(x), Math.ceil(y))));
    float BotRight = (float)(PerlinNoise.pseudoRandom((int)Math.pow(Math.ceil(x), Math.floor(y))));
    float Top = TopLeft + ((TopLeft-TopRight) * x % 1);
    float Bot = BotLeft + ((BotLeft-BotRight) * x % 1);
    return Bot + ((Bot-Top) * y % 1);
  }
  public static float pseudoRandom(int dist){
    float rollover = 0;
    for (int i = 0; i < dist; i++) {
      rollover += seed2;
      rollover%=seed;
    }
    return  rollover/seed;
  }
}
