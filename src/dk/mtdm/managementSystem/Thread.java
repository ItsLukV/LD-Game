package dk.mtdm.managementSystem;

public abstract class Thread extends java.lang.Thread{
  
  public boolean atWork = false;
  /**
   * builds ontop of thread system to allow all operations to completete smoothly
   */
  @Override
  public void run() {
    if(atWork) return;
    atWork = true;
    super.run();
    Run();
    atWork = false;
    return;
  }
  protected abstract void Run();
}