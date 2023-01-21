package dk.mtdm;

import dk.mtdm.itemsAndMore.Block;
import dk.mtdm.itemsAndMore.BlockPicker;
import dk.mtdm.itemsAndMore.BlockTextures;
import dk.mtdm.itemsAndMore.BlockTypes;
import processing.core.PApplet;

public class Sketch extends PApplet {
  private Block block;

  @Override
  public void settings() {
    size(1000,1000);
  }

  @Override
  public void setup() {
    BlockTextures.loadBlockTextures(this);
    try {
      block = BlockPicker.picker(BlockTypes.bedrock, new LDVector(100, 100));
    } catch (Exception e) {
      // TODO: handle exception
    }
    // TODO Auto-generated method stub
  }

  @Override
  public void draw() {
    block.show(this);
    // TODO Auto-generated method stub
  }
}
