package dk.mtdm;

import dk.mtdm.managementSystem.world.Chunk;
import dk.mtdm.managementSystem.world.WorldGenThread;

public class test {
  public static void main(String[] args) {
    WorldGenThread t = new WorldGenThread(0, 25, 10,new Chunk(0, 32, 100, 10, 10));
    System.out.println("start");
    t.start();
    System.out.println("run");
    // t.run();
    System.out.println("donw");
  }
}
