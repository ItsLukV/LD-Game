package dk.mtdm;

import dk.mtdm.itemsAndMore.Blocks.Block;
import dk.mtdm.itemsAndMore.Blocks.BlockTextures;
import dk.mtdm.itemsAndMore.Blocks.GrassBlock;
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
    size(800, 450);
  }
  /**
   * TODO: write javadoc
   */
  @Override
  public void setup() {
    BlockTextures.loadBlockTextures(this);
    MiscTextures.loadBlockTextures(this);

    player = new Player(new LDVector(0, 0-Block.getHeight()));
    World.setup(3,World.get_HEIGHT(),20);
  }


  /**
   * TODO: write javadoc
   */
  @Override
  public void draw() {
    background(0,0,255);
    push();
    translate(-player.getPos().getX()-Player.width/2+width/2, -player.getPos().getY()-Player.height/2+height/2);

    World.show(g,(int) (player.getPos().getX()/World.get_CHUNK_WIDTH()/Block.getWidth())-2,(int) (player.getPos().getX()/World.get_CHUNK_WIDTH()/Block.getWidth())+1);
    player.tick();
    player.show(g);
    pop();
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
