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

  void add(LDVector vector, LocationTypes location);

}