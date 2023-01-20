package dk.mtdm.itemsAndMore;

import java.util.concurrent.BlockingDeque;

import processing.core.PApplet;
import processing.core.PGraphics;

public class Block {
    private int x,y,w;
    private BlockTypes id;
    public Block(int x, int y, BlockTypes id) {
        this.x = x;
        this.y = y;
        this.w = 32;
        this.id = id;
    }
    

    public void show(PApplet p) {
        try {
            p.image(BlockTextures.picker(id), x, y, w, w);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
