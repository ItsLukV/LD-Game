package dk.mtdm.itemsAndMore.items;

import dk.mtdm.itemsAndMore.ability.Mining;
import dk.mtdm.itemsAndMore.texureFiles.ItemTexture;

public class Pickaxe extends Item {
/**
 * an item that allows the player to break blocks in the world
 */
    public Pickaxe() {
        super(ItemTypes.pickaxe, 1, ItemTexture.getPickaxeTexture(), new Mining());
    }

    @Override
    public void tick() {

    }
}
