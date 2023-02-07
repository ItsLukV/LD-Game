package dk.mtdm.managementSystem;

public abstract class Thread extends java.lang.Thread{
  
  public boolean atWork = false;
  /**
 * TODO: write javadoc
 */
  @Override
  public void run() {
    atWork = true;
    super.run();
    Run();
    atWork = false;
  }
  public abstract void Run();
}