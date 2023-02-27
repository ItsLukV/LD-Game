package dk.mtdm.location;

import dk.mtdm.exceptions.MissingDataException;
import dk.mtdm.itemsAndMore.Blocks.Block;
import dk.mtdm.managementSystem.world.World;

/**
 * int should only be float or int
 * @param x
 * @param y
 * @param location
 */
public class WorldWideLocation {
  int x;
  int y;
  LocationTypes location;
  Integer chunkID = null;
  
  public static WorldWideLocation create(LDVector vector, LocationTypes location){
    return new WorldWideLocation(vector.getX(), vector.getY(), location);
  }
  public static WorldWideLocation create(int x, int y, LocationTypes location){
    return new WorldWideLocation(x, y, location);
  }
  public static WorldWideLocation create(int x, int y, LocationTypes location, int chunkID){
    return new WorldWideLocation(x, y, location, chunkID);
  }

  private WorldWideLocation (int x, int y, LocationTypes location, int chunkID){
    this.x = x;
    this.y = y;
    this.location = location;
    this.chunkID = chunkID;
  }
  private WorldWideLocation (int x, int y, LocationTypes location){
    this.x = x;
    this.y = y;
    this.location = location;
  }

  /**
   * only relevant for coordinates given in relative
   * @param ID
   */
  public void setChunkID(int ID){
    chunkID = ID;
  }

  public int getChunkID() throws MissingDataException{
    switch (location) {
      case relative:
        if(chunkID != null){
          return chunkID;
        }else{
          throw new MissingDataException("relative location is incapable of providing CHUNKID without a set CHUNKID");
        }
      
      case global:
        return globalToChunkID(this.x);
      
      case canvas:
        int out = canvasToGlobal_X(x);
        return globalToChunkID(out);
      
      case unkown:
        throw new MissingDataException("no known location int given, it is not posible to generate specific information without specific data");
      default:
        throw new MissingDataException("location int not found, likely null, please set location int");
    }
  }
  public LDVector getRelative() throws MissingDataException{
    switch (location) {
      case relative:
        return new LDVector((int)x, (int)y);
      
      case global:
        return new LDVector(globalToRelative_X((int)x), (int)y);
      
      case canvas:
        return new LDVector(globalToRelative_X(canvasToGlobal_X(x)), canvasToGlobal_Y(y));
      
      case unkown:
        throw new MissingDataException("no known location int given, it is not posible to generate specific information without specific data");
      default:
        throw new MissingDataException();
    }
  }
  public LDVector getGlobal() throws MissingDataException{
    switch (location) {
      case relative:
      if(chunkID != null){
        return new LDVector(relativToGlobal_X((int)x),(int)y);
      }else{
        throw new MissingDataException("relative location is incapable of providing GLOBAL without a set CHUNKID");
      }
    
    case global:
      return new LDVector((int)x, (int)y);
    
    case canvas:
      return new LDVector(canvasToGlobal_X(x), canvasToGlobal_Y(y));
    
    case unkown:
      throw new MissingDataException("no known location int given, it is not posible to generate specific information without specific data");
    default:
      throw new MissingDataException();
    }
  }
  public LDVector getCanvas() throws MissingDataException{
    switch (location) {
      case relative:
        if(chunkID != null){
          return new LDVector(globalToCanvas_X(relativToGlobal_X((int)x)),(int)y);
        }else{
          throw new MissingDataException("relative location is incapable of providing GLOBAL without a set CHUNKID");
        }
      
      case global:
        return new LDVector(globalToCanvas_X((int)x), globalToCanvas_Y((int)y));
      
      case canvas:
        return new LDVector((int) x, (int) y);
      
      case unkown:
        throw new MissingDataException("no known location int given, it is not posible to generate specific information without specific data");
      default:
        throw new MissingDataException();
    }
  }
  
  private int globalToChunkID(int x) {
    return globalToChunkID((float) x);
  }
  private int globalToChunkID(float x) {
    return (int)Math.floor(x / (float)World.get_CHUNK_WIDTH());
  }
  private int canvasToGlobal_X(int x){
    return (int) x/Block.getWidth();
  }
  private int globalToRelative_X(int x){
    if(x > 0){
      return Math.abs(x%World.get_CHUNK_WIDTH());
    }else{
      return World.get_CHUNK_WIDTH()-Math.abs(x%World.get_CHUNK_WIDTH())-1;
    }
  }
  private int canvasToGlobal_Y(int y){
    int out = (int)((float)y/Block.getHeight());
    out *= -1;
    if(out < 0){
      return 0;
    }else if(out > World.get_HEIGHT()){
      return World.get_HEIGHT()-1;
    }
    return out;
  }
  private int relativToGlobal_X(int x){
    return chunkID*World.get_CHUNK_WIDTH()+x;
  }
  private int globalToCanvas_X(int x){
    return x*Block.getWidth();
  }
  private int globalToCanvas_Y(int y){
    return -y*Block.getHeight();
  }

  public void setPosition(LDVector vector, LocationTypes location){
    this.x = (int) vector.getX();
    this.y = vector.getY();
    this.location = location;
  }
  public WorldWideLocation copy(){
    return new WorldWideLocation(x, y, location, chunkID);
  }
  public void add(LDVector vector, LocationTypes location){
    switch (location) {
      case relative:
      case global:
      case unkown:
        if(this.location == LocationTypes.relative || this.location == LocationTypes.global || this.location == LocationTypes.unkown){
          x += vector.getX();
          y += vector.getY();
        }else{
          x += globalToCanvas_X(vector.getX());
          y += globalToCanvas_Y(vector.getY());
        }
        break;
      case canvas:
        if (this.location == LocationTypes.canvas) {
          x += vector.getX();
          y += vector.getY();
        }else{
          x += canvasToGlobal_X(vector.getX());
          y += canvasToGlobal_Y(vector.getY());
        }
    
      default:
        break;
    }
  }
}
