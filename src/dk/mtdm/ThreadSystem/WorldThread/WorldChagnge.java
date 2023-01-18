package dk.mtdm.ThreadSystem.WorldThread;

import dk.mtdm.Point;

public class WorldChagnge {

  private ChangeType action;
  private Point location;

  public WorldChagnge(ChangeType action,int x,int y){
    this.action = action;
    this.location = new Point(x,y);
  }
  public ChangeType getAction(){
    return action;
  }
  public Point getlocation(){
    return location;
  }
  public int getX(){
    return location.getX();
  }
  public int getY(){
    return location.getY();
  }
}
