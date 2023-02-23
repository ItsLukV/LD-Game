package dk.mtdm.location;

import dk.mtdm.exceptions.MissingDataException;
import dk.mtdm.itemsAndMore.Blocks.Block;
import dk.mtdm.managementSystem.world.World;

/**
 * type should only be float or int
 * @param x
 * @param y
 * @param location
 */
public class WorldWideLocation<type> {
  type x;
  type y;
  LocationTypes location;
  Integer chunkID = null;
  
  public static WorldWideLocation<Integer> create(LDVector vector, LocationTypes location){
    return new WorldWideLocation<Integer>(vector.getX(), vector.getY(), location);
  }
  public static WorldWideLocation<Integer> create(int x, int y, LocationTypes location){
    return new WorldWideLocation<Integer>(x, y, location);
  }
  public static WorldWideLocation<Float> create(float x, float y, LocationTypes location){
    return new WorldWideLocation<Float>(x, y, location);
  }
  
  private WorldWideLocation (type x, type y, LocationTypes location){
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
        throw new MissingDataException("no known location type given, it is not posible to generate specific information without specific data");
      default:
        throw new MissingDataException("location type not found, likely null, please set location type");
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
        throw new MissingDataException("no known location type given, it is not posible to generate specific information without specific data");
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
      throw new MissingDataException("no known location type given, it is not posible to generate specific information without specific data");
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
        throw new MissingDataException("no known location type given, it is not posible to generate specific information without specific data");
      default:
        throw new MissingDataException();
    }
  }
  
  private int globalToChunkID(type x) {
    return globalToChunkID((float) x);
  }
  private int globalToChunkID(float x) {
    return (int)Math.floor(x / (float)World.get_CHUNK_WIDTH());
  }
  private int canvasToGlobal_X(type x){
    return (int) x/Block.getWidth();
  }
  private int globalToRelative_X(int x){
    if(x > 0){
      return Math.abs(x%World.get_CHUNK_WIDTH());
    }else{
      return World.get_CHUNK_WIDTH()-Math.abs(x%World.get_CHUNK_WIDTH())-1;
    }
  }
  private int canvasToGlobal_Y(type y){
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
}
