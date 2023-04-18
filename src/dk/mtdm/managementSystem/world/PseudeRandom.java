package dk.mtdm.managementSystem.world;

import java.util.function.Function;

public class PseudeRandom {
  private final static float dist = 2f;
  private static float a = 10f;
  private static float alpha = 9f;
  private static float b = 8f;
  private static float beta = 7f;
  private static float step = 0.1f;
  private static Function<float[],Float> x = in -> (-in[0]+alpha*f(in[0])-b*f(in[1])-b*f(in[2]))*step;
  private static Function<float[],Float> y = in -> (-in[1]+b*f(in[0])-beta*f(in[1])-a*f(in[2]))*step;
  private static Function<float[],Float> z = in -> (-in[2]+b*f(in[0])-a*f(in[1])-f(in[2]))*step;
  private static long dimensionTimer = System.currentTimeMillis();
  /**
   * @param x
   * @param y
   * @param z seed
   * @return
   */
  public static float attractor_3Cell_CNN(float x, float y, float z){
    // System.out.println(x + " " + y + " " + z);
    float[] post = {x,y,z};
    float[] temp = {x,y,z};
    for (float i = 0; i < dist; i++){
      temp[0] = PseudeRandom.x.apply(post);
      temp[1] = PseudeRandom.y.apply(post);
      temp[2] = PseudeRandom.z.apply(post);
      
      post[0] = temp[0];
      post[1] = temp[1];
      post[2] = temp[2];
    }
    dimensionTimer = System.currentTimeMillis();
    return (float) Math.abs(post[0]*post[1]*post[2])%1; 
  }
  private synchronized static float f(float x){
    return 0.5f*(Math.abs(x+1)-Math.abs(x-1));
  }
  public static void update(int dimensionID) {
    while(System.currentTimeMillis()-PseudeRandom.dimensionTimer < 1000){
      try {
        Thread.sleep(200);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    float dimensionalConstant = (dimensionID)/(dist/step);
    PseudeRandom.a = 10f + 10f * dimensionalConstant;
    PseudeRandom.alpha = 9f + 9f * dimensionalConstant;
    PseudeRandom.b = 8f + 8f * dimensionalConstant;
    PseudeRandom.beta = 7f + 7f * dimensionalConstant;
    
    PseudeRandom.x = in -> (-in[0]+alpha*f(in[0])-b*f(in[1])-b*f(in[2]))*step;
    PseudeRandom.y = in -> (-in[1]+b*f(in[0])-beta*f(in[1])-a*f(in[2]))*step;
    PseudeRandom.z = in -> (-in[2]+b*f(in[0])-a*f(in[1])-f(in[2]))*step;
  }
}
