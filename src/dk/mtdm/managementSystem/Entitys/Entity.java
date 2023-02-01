package dk.mtdm.managementSystem.Entitys;

import dk.mtdm.LDVector;
import processing.core.PGraphics;

public abstract class Entity {
  protected LDVector pos;
  protected int width;
  protected int height;

  abstract public void show(PGraphics g);

  abstract public void tick();
}
