package dk.mtdm;

import dk.mtdm.managementSystem.world.PerlinNoise;
public class test {
  public static void main(String[] args) {
    int seed = 1253477;
    PerlinNoise.start(seed);
    System.out.println(PerlinNoise.getNoise(0.2f, 0.1f));
    System.out.println(PerlinNoise.getNoise(0.2f, 0.15f));
  }
}
