package dk.mtdm;
//TODO: add comments
import dk.mtdm.itemsAndMore.Block;
import dk.mtdm.itemsAndMore.BlockPicker;
import dk.mtdm.itemsAndMore.BlockTextures;
import dk.mtdm.itemsAndMore.BlockTypes;
import dk.mtdm.managementSystem.Entitys.Player;
import dk.mtdm.managementSystem.world.World;
import dk.mtdm.misc.miscTextures.MiscTextures;
import processing.core.PApplet;

public class Sketch extends PApplet {
  private Block block;
  private static Player player;
  public static int offsetX = player.getPos().getX() + Player.width / 2;
  public static int offsetY = player.getPos().getY() + Player.height / 2;  
  
  @Override
  public void settings() {
    size(1000, 1000);
  }

  @Override
  public void setup() {
    BlockTextures.loadBlockTextures(this);
    MiscTextures.loadBlockTextures(this);

    block = BlockPicker.getBedrock(BlockTypes.bedrock, new LDVector(100, 100));
    player = new Player(new LDVector(0, 100));
    System.out.println(player.collisionWith(block));
    World.setup(100, 100, 20);
  }

  @Override
  public void draw() {
    background(220);
    World.show(g,-1,1);
    block.show(g);
    player.tick();
    player.show(g);
    translate(offsetX, offsetY);
  }

  @Override
  public void keyReleased(){
    player.keyReleased(keyCode == 37, keyCode == 39, keyCode == 38, keyCode == 40);
  }

  public void keyPressed() {
    player.keyPressed(keyCode == 37, keyCode == 39, keyCode == 38, keyCode == 40);
  }
}
