package dk.mtdm.ThreadSystem;

public abstract class Thread extends java.lang.Thread{
  
  public boolean atWork = false;
  
  @Override
  public void run() {
    atWork = true;
    super.run();
    Run();
    atWork = false;
  }
  @Override
  public synchronized void start() {
    super.start();
    Start();
  }
  protected abstract void Run();
  protected abstract void Start();
}
