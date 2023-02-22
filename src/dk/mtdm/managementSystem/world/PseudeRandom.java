package dk.mtdm.managementSystem.world;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PseudeRandom {
  private final static float dist = 2f;
  private final static float a = 10f;
  private final static float alpha = 10f;
  private final static float b = 10f;
  private final static float beta = 10f;
  private final static float step = 0.1f;
  /**
   * 
   * @param x
   * @param y
   * @param z seed
   * @return
   */
  public static float attractor_3Cell_CNN(float x, float y, float z){
    float out = 0;
    // System.out.println(x + " " + y + " " + z);
    for (float i = 0; i < dist; i++){
      x=(-x+alpha*f(x)-b*f(y)-b*f(z))*step;
      y=(-y+b*f(x)-beta*f(y)-a*f(z))*step;
      z=(-z+b*f(x)-a*f(y)-f(z))*step;
    }
    out = Math.abs(x*y*z);
    out = (float)(out-Math.floor(out));
    // System.out.println(out + " " + x + " " + y + " " + z);
    return (float)out; 
  }
  private synchronized static float f(float x){
    return 0.5f*(Math.abs(x+1)-Math.abs(x-1));
  }
  
  public synchronized static float Float(int x, int y,int seed) {
    try {
      long point = hash(x, y,seed);
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.update(longToBytes(point));
      byte[] digest = md.digest();
      int value = Math.abs(bytesToInt(digest));
      return ((float) value / Integer.MAX_VALUE)*2-1;
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("MD5 algorithm not found", e);
    }
  }
  private synchronized static long hash(int x, int y, int seed) {
    long h = 17;
    h = h * seed + x;
    h = h * seed + y;
    return h;
  }
  private synchronized static byte[] longToBytes(long value) {
    byte[] result = new byte[8];
    for (int i = 7; i >= 0; i--) {
      result[i] = (byte) (value & 0xff);
      value >>>= 8;
    }
    return result;
  }
  private synchronized static int bytesToInt(byte[] bytes) {
    int value = 0;
    for (int i = 0; i < 4; i++) {
      value <<= 8;
      value |= bytes[i] & 0xff;
    }
    return value;
  }
}
