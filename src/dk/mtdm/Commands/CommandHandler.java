package dk.mtdm.Commands;

import dk.mtdm.Sketch;
import dk.mtdm.exceptions.MissingDataException;
import dk.mtdm.itemsAndMore.Blocks.BlockPicker;
import dk.mtdm.itemsAndMore.Blocks.BlockTypes;
import dk.mtdm.itemsAndMore.items.Item;
import dk.mtdm.itemsAndMore.items.ItemPicker;
import dk.mtdm.itemsAndMore.items.ItemTypes;
import dk.mtdm.location.LDVector;
import dk.mtdm.location.LocationTypes;
import dk.mtdm.location.WorldWideLocation;
import dk.mtdm.managementSystem.Entitys.Player;
import dk.mtdm.managementSystem.world.ChunkList;
import dk.mtdm.managementSystem.world.World;
import processing.core.PGraphics;

public class CommandHandler {
    private TextInputBox textInputBox;

    public CommandHandler() {
        textInputBox = new TextInputBox();
    }

    public void show(PGraphics g) {
        textInputBox.show(g);
    }

    public void keyPressed(int keyCode) {
        textInputBox.keyPressed(keyCode);
    }

    public void execute() {
        if(textInputBox.getText().charAt(0) == '.') {
            String text = textInputBox.getText().substring(1);
            String[] command = text.split(" ");
            switch (command[0].toUpperCase()) {
                case "GIVE" -> {
                    Item item = translateStringToItem(command[1]);
                    int amount = 1;
                    try {
                        amount = command.length == 2 ? 1 : Integer.parseInt(command[2]);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    Sketch.player.getInventory().giveItem(item, amount);
                }
                case "TEST" -> Test();
                case "NOCLIP" -> Player.noClip = !Player.noClip;
                case "PING" -> System.out.println("Pong!");
            }
            textInputBox.restartText();
        }
    }

    private void Test() {
        WorldWideLocation playerPos;
        playerPos = Player.getPos();
        try {
            for(int i = 0; i < World.get_CHUNK_WIDTH(); i++) {
                for(int j = 0; j < 10; j++) {
                    WorldWideLocation blockPos = WorldWideLocation.create(i, j, LocationTypes.relative);
                    ChunkList.getChunk(playerPos.getChunkID()).setBlock(blockPos, BlockTypes.stone);
                }
            }
            for(int i = 1; i < World.get_CHUNK_WIDTH() - 1; i++) {
                for(int j = 1; j < 9; j++ ) {
                    WorldWideLocation blockPos = WorldWideLocation.create(i, j, LocationTypes.relative);
                    ChunkList.getChunk(playerPos.getChunkID()).setBlock(blockPos, BlockTypes.air);
                }
            }

            WorldWideLocation blockPos = WorldWideLocation.create(3, 3, LocationTypes.relative);
            ChunkList.getChunk(playerPos.getChunkID()).setBlock(blockPos, BlockTypes.stone);


            blockPos = WorldWideLocation.create(3, 1, LocationTypes.relative);
            ChunkList.getChunk(playerPos.getChunkID()).setBlock(blockPos, BlockTypes.stone);


        } catch (MissingDataException e) {
            e.printStackTrace();
        }
    }

    private Item translateStringToItem(String str) {
        ItemTypes type = null;
        try {
            type = ItemTypes.valueOf(str.toLowerCase());
            return ItemPicker.picker(type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
