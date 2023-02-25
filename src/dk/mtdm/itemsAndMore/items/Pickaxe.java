package dk.mtdm.itemsAndMore.items;

import dk.mtdm.itemsAndMore.texureFiles.ItemTexture;
import processing.core.PImage;

public class Pickaxe extends Item{
    /**
     * TODO: write javadoc
     *
     * @param id
     * @param stackSize
     * @param texture
     */
    public Pickaxe() {
        super(ItemTypes.pickaxe, 1, ItemTexture.getPickaxeTexture());
    }

    @Override
    public void tick() {

    }
}
