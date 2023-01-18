package dk.mtdm.ThreadSystem.WorldThread;

import java.util.ArrayList;

import dk.mtdm.ThreadSystem.Thread;
import dk.mtdm.ThreadSystem.WorldThread.ChangeType;

public class WorldThread extends Thread {
  public static ArrayList<WorldChagnge> chagneQueue = new ArrayList<WorldChagnge>();
  
  @Override
  protected void Start() {
    // TODO Auto-generated method stub
    
  }
  
  @Override
  protected void Run() {
    queueCheck();
  }
  private void queueCheck(){
    WorldChagnge currentChagnge = chagneQueue.get(chagneQueue.size()-1);
    switch (currentChagnge.getAction()) {
      case destroyBlock:
        
        break;
    
      default:
        break;
    }
  }
  
}
