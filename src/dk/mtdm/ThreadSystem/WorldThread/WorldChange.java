package dk.mtdm.ThreadSystem.WorldThread;

import dk.mtdm.Point;

public class WorldChange {

  private WorldChangeType action;
  private Point location;
  private int callerID;

  public WorldChange(WorldChangeType action,int x,int y, int playerID){
    this.action = action;
    this.location = new Point(x,y);
    this.callerID = playerID;
  }
  public WorldChange(WorldChangeType action,int x,int y){
    this.action = action;
    this.location = new Point(x,y);
    this.callerID = -1;
  }
  public WorldChangeType getAction(){
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
  public int getID(){
    return callerID;
  }
}
