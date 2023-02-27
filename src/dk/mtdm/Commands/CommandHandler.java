package dk.mtdm.Commands;

import dk.mtdm.Sketch;
import dk.mtdm.itemsAndMore.items.Item;
import dk.mtdm.itemsAndMore.items.ItemPicker;
import dk.mtdm.itemsAndMore.items.ItemTypes;
import dk.mtdm.itemsAndMore.items.Stick;
import processing.core.PApplet;
import processing.core.PGraphics;

public class CommandHandler {
    private TextInputBox textInputBox;

    public CommandHandler(PApplet p) {
        textInputBox = new TextInputBox(p);
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
                    Sketch.player.getInventory().giveItem(item,1);
                }
                case "PING" -> System.out.println("Pong!");
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
