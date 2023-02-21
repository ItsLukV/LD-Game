package dk.mtdm.managementSystem.world;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PseudeRandom {
  private final static double runs = 2;
  private final static double a = 10;
  private final static double alpha = 10;
  private final static double b = 10;
  private final static double beta = 10;
  private final static double step = 0.1;
  /**
   * 
   * @param x
   * @param y
   * @param z seed
   * @return
   */
  public static float attractor_3Cell_CNN(double x, double y, double z){
    double out = 0;
    for (int i = 0; i < runs; i++) {
      x=(-x+alpha*f(x)-b*f(y)-b*f(z))*step;
      y=(-y+b*f(x)-beta*f(y)-a*f(z))*step;
      z=(-z+b*f(x)-a*f(y)-f(z));
    }
    out = Math.abs(x*y*z);
    out = (out%1)*2-1;
    // System.out.println(out);
    return (float)out; 
  }
  private synchronized static double f(double x){
    return 0.5*(Math.abs(x+1)-Math.abs(x-1));
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
