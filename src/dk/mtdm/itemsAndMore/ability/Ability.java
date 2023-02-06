package dk.mtdm.itemsAndMore.ability;

import processing.core.PGraphics;

public abstract class Ability {
  public abstract void tick();
  public abstract void show(PGraphics g);
}
