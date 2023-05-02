package dk.mtdm.Commands;

import java.io.IOException;

import dk.mtdm.Sketch;
import dk.mtdm.exceptions.IncorrectSaveSettingsLoaded;
import dk.mtdm.itemsAndMore.items.Item;
import dk.mtdm.itemsAndMore.items.ItemPicker;
import dk.mtdm.itemsAndMore.items.ItemTypes;
import dk.mtdm.location.LDVector;
import dk.mtdm.location.LocationTypes;
import dk.mtdm.location.WorldWideLocation;
import dk.mtdm.managementSystem.Entitys.Player;
import dk.mtdm.managementSystem.save.Save;
import dk.mtdm.managementSystem.save.Load;
import dk.mtdm.managementSystem.world.chunk.ChunkList;
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
                case "NOCLIP" -> {
                    Player.noClip = !Player.noClip;
                    System.out.println("noClip:" + Player.noClip);
                }
                case "PING" -> System.out.println("Pong!");
                case "DIM" -> {
                    try{
                        ChunkList.setDimensionID(Integer.parseInt(command[1]));
                    }catch(IndexOutOfBoundsException index){
                        System.out.println(ChunkList.getDimensionID());
                    }catch(NumberFormatException number){
                        System.out.println("inproper use of DIM\n\".DIM\" will provide current dimension ID\n\".DIM {num}\" will alow you to set a new dimension ID");
                    }
                }
                case "SAVE" -> {
                    try {
                        Save prog = new Save("hommer");
                        prog.state();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                case "LOAD" -> {
                    try {
                        Load.loadWorld("hommer");
                    } catch (IncorrectSaveSettingsLoaded e) {
                        e.printStackTrace();
                    }
                }
            }
            textInputBox.restartText();
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
