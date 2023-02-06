package dk.mtdm;

import dk.mtdm.exceptions.MissingBlockTypeException;
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
  private Player player;

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
    block.show(g);
    player.tick();
    player.show(g);
  }

  @Override
  public void keyReleased(){
    player.keyReleased(keyCode == 37, keyCode == 39, keyCode == 38, keyCode == 40);
  }

  public void keyPressed() {
    player.keyPressed(keyCode == 37, keyCode == 39, keyCode == 38, keyCode == 40);
  }
}
