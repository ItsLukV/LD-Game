package dk.mtdm;

import dk.mtdm.itemsAndMore.Blocks.Block;
import dk.mtdm.itemsAndMore.texureFiles.BlockTextures;
import dk.mtdm.itemsAndMore.texureFiles.ItemTexture;
import dk.mtdm.location.LDVector;
import dk.mtdm.managementSystem.Entitys.Player;
import dk.mtdm.managementSystem.world.World;
import dk.mtdm.misc.miscTextures.MiscTextures;
import processing.core.PApplet;

public class Sketch extends PApplet {
  private static Player player;
  public static int KeyCode;

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
    ItemTexture.loadBlockTextures(this);

    World.setup(3, World.get_HEIGHT(), 20);
    player = new Player(new LDVector(0, -300));
  }

  /**
   * TODO: write javadoc
   */
  @Override
  public void draw() {
    background(0,0,0);
    push();
    translate(-player.getPos().getX() - Player.width / 2 + width / 2,
        -player.getPos().getY() - Player.height / 2 + height / 2);
    // System.out.println(World.CanvasToGlobal(player.getPos()).getX() + " " +
    // World.CanvasToGlobal(player.getPos()).getY() + "\n" + player.getPos().getX()
    // + " " + player.getPos().getY() + "\n");
    World.show(g, (int) (player.getPos().getX() / World.get_CHUNK_WIDTH() / Block.getWidth()) - 2,
        (int) (player.getPos().getX() / World.get_CHUNK_WIDTH() / Block.getWidth()) + 1);
    player.draw(g);
    pop();
    player.drawWithoutTranslate(g);
    player.tick(g);
    // System.out.println(player.getPos().getX() + " " + player.getPos().getY() +
    // "\n" + World.CanvasToGlobal(player.getPos()).getX() + " " +
    // World.CanvasToGlobal(player.getPos()).getY() + "\n");
  }

  /**
   * TODO: write javadoc
   */
  @Override
  public void keyReleased() {
    boolean left = keyCode == 37 || keyCode == 65;
    boolean right = keyCode == 39 || keyCode == 68;
    boolean up = keyCode == 38 || keyCode == 87;
    boolean down = keyCode == 40 || keyCode == 83;
    boolean e = keyCode == 69;
    player.keyReleased(left, right, up, down);
  }

  /**
   * TODO: write javadoc
   */
  public void keyPressed() {
    KeyCode = keyCode;
    boolean left = keyCode == 37 || keyCode == 65;
    boolean right = keyCode == 39 || keyCode == 68;
    boolean up = keyCode == 38 || keyCode == 87;
    boolean down = keyCode == 40 || keyCode == 83;
    boolean e = keyCode == 69;
    player.keyPressed(left, right, up, down, e);
  }
}
