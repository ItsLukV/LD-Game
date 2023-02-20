package dk.mtdm;

import dk.mtdm.managementSystem.world.PerlinNoise;

public class test {
  public static void main(String[] args) {
    PerlinNoise.start((int) (Math.random()*Integer.MAX_VALUE), (int)(Math.random()*Integer.MAX_VALUE));
    for (float i = 0; i < 100; i++) {
      for (float j = 0; j < 100; j++) {
        System.out.println(PerlinNoise.getNoise(i/16, j/16));
      }
    }
  }
}
