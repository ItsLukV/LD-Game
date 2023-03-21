package dk.mtdm.itemsAndMore.ability;

import processing.core.PGraphics;

public abstract class Ability {
  /**
   * Updates the ability, but only when the item is selected in the hotbar
   */
  public abstract void tick();

  /**
   * Shows the ability, but only when the item is selected in the hotbar
   *
   * @param g
   */
  public abstract void show(PGraphics g);

  /**
   * Runs when the item with this ability is selected
   */
  public abstract void selected();

  /**
   * Runs when a item that is selected in the hotbar with this ability is selected
   * and clicks on the world
   */
  public abstract void clicked(int x, int y);

  /**
   * Runs even if the item is not selected
   */
  public abstract void passive();
}
