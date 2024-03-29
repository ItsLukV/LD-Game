package dk.mtdm.managementSystem.Entitys;

import dk.mtdm.location.LDVector;
import dk.mtdm.location.WWL;
import processing.core.PGraphics;

public abstract class Entity {
  protected static WWL pos;
  protected int width;
  protected int height;
  protected LDVector speed = new LDVector(0, 0);

  /**
   * shows the entity
   * 
   * @param g
   */
  abstract public void draw(PGraphics g);

  /**
   * Updates the entity
   */
  abstract public void tick();
}
