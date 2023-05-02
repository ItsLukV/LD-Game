package dk.mtdm.itemsAndMore.ability;

import dk.mtdm.Sketch;
import dk.mtdm.exceptions.MissingBlockTypeException;
import dk.mtdm.exceptions.MissingDataException;
import dk.mtdm.itemsAndMore.Blocks.Block;
import dk.mtdm.itemsAndMore.Blocks.BlockTypes;
import dk.mtdm.itemsAndMore.texureFiles.breaking.BreakingTexures;
import dk.mtdm.location.LDVector;
import dk.mtdm.location.LocationTypes;
import dk.mtdm.location.WWL;
import dk.mtdm.location.WorldWideLocation;
import dk.mtdm.managementSystem.Entitys.Player;
import dk.mtdm.managementSystem.world.World;
import dk.mtdm.managementSystem.world.chunk.ChunkList;
import processing.core.PGraphics;

public class Mining extends Ability {
    @Override
    public void tick() {

    }

    @Override
    public void show(PGraphics g) {

    }

    @Override
    public void selected() {

    }

    @Override
    public void clicked(int x, int y) {
        x -= -Player.getCanvas().getX() - Player.width / 2 + Sketch.width / 2;
        y -= -Player.getCanvas().getY() - Player.height / 2 + Sketch.height / 2;
        WWL pos = WWL.create(x, y, LocationTypes.canvas);
        Block block;
        pos.add(new LDVector(0, -32), LocationTypes.canvas);
        try {
            block = World.getBlock(pos);
        } catch (MissingBlockTypeException e) {
            e.printStackTrace();
            return;
        }
        if (!block.isBreakable())
            return;
        if (block.getBreakLevel() < BreakingTexures.getBreakingTextures().length - 1) {
            block.setBreakLvl(block.getBreakLevel() + 1);
        } else {
            try {
                ChunkList.getChunk(pos.getChunkID()).setBlock(pos, BlockTypes.air);
            } catch (MissingDataException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void passive() {

    }
}
