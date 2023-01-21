package dk.mtdm.managementSystem;

public abstract class Thread extends java.lang.Thread{
  
  public boolean atWork = false;
  
  @Override
  public void run() {
    atWork = true;
    super.run();
    Run();
    atWork = false;
  }
  protected abstract void Run();
}