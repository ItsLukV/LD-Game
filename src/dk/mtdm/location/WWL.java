package dk.mtdm.location;

import dk.mtdm.exceptions.MissingDataException;

public interface WWL {

  /**
   * only relevant for coordinates given in relative
   * @param ID
   */
  void setChunkID(int ID);

  int getChunkID() throws MissingDataException;

  LDVector getRelative() throws MissingDataException;

  LDVector getGlobal() throws MissingDataException;

  LDVector getCanvas() throws MissingDataException;

  void setPosition(LDVector vector, LocationTypes location);

  WWL copy();

  public void add(LDVector vector, LocationTypes location);

  public void setToCanvas() throws MissingDataException;

  public void setX(int x, LocationTypes location);

  public void setY(int y, LocationTypes location);

}
