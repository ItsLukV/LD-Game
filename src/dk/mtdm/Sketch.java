package dk.mtdm;

import dk.mtdm.itemsAndMore.BlockTextures;
import dk.mtdm.managementSystem.Entitys.Player;
import dk.mtdm.managementSystem.world.World;
import dk.mtdm.misc.miscTextures.MiscTextures;
import processing.core.PApplet;

public class Sketch extends PApplet {
  private static Player player;
  // public static int offsetX = player.getPos().getX() + Player.width / 2;
  // public static int offsetY = player.getPos().getY() + Player.height / 2;  
  /**
   * TODO: write javadoc
   */
  @Override
  public void settings() {
    size(1000, 1000);
  }
  /**
   * TODO: write javadoc
   */
  @Override
  public void setup() {
    BlockTextures.loadBlockTextures(this);
    MiscTextures.loadBlockTextures(this);

    player = new Player(new LDVector(0, 100));
    
    World.setup(3,World.get_HEIGHT(), 20);
  }
  /**
   * TODO: write javadoc
   */
  @Override
  public void draw() {
    background(0,0,255);
    World.show(g,0,10);
    player.tick();
    player.show(g);
  }
  /**
   * TODO: write javadoc
   */
  @Override
  public void keyReleased(){
    player.keyReleased(keyCode == 37, keyCode == 39, keyCode == 38, keyCode == 40);
  }
  /**
   * TODO: write javadoc
   */
  public void keyPressed() {
    player.keyPressed(keyCode == 37, keyCode == 39, keyCode == 38, keyCode == 40);
  }
}
