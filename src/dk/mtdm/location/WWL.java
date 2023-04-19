package dk.mtdm.location;

import dk.mtdm.exceptions.MissingDataException;

public class WWL extends WorldWideLocation {

  public WWL(int x, int y, LocationTypes location) {
    super(x, y, location);
  }
  public WWL(int x, int y, LocationTypes location, int chunkID) {
    super( x, y, location, chunkID);
  }

  public static WWL create(LDVector vector, LocationTypes location){
    return new WWL(vector.getX(),vector.getY(), location);
  }

  public static WWL create(int x, int y, LocationTypes location){
    return new WWL(x, y, location);
  }
  public static WWL create(int x, int y, LocationTypes location, Integer chunkID){
    return new WWL(x, y, location, chunkID);
  }

  public WWL copy(){
    if(chunkID == null){
      return new WWL(x, y, location);
    }else{
      return new WWL(x, y, location, chunkID);
    }
  }
}
