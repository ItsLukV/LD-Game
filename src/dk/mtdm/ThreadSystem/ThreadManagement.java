package dk.mtdm.ThreadSystem;

import dk.mtdm.ThreadSystem.WorldThread.WorldThread;

public class ThreadManagement {
  
  private static final int numberOfDifferentThreads = 1;
  
  public static Thread[] initializeAllThreads(){
    Thread[] threads = new Thread[numberOfDifferentThreads];
    threads[0] = initializeWorldThread();
    return threads;
  }
  
  private static WorldThread initializeWorldThread(){
    return new WorldThread();
  }
}
