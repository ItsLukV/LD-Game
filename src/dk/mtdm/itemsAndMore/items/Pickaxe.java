package dk.mtdm.itemsAndMore.items;

import dk.mtdm.itemsAndMore.ability.Mining;
import dk.mtdm.itemsAndMore.texureFiles.ItemTexture;

public class Pickaxe extends Item {
    /**
     * TODO: write javadoc
     *
     * @param id
     * @param stackSize
     * @param texture
     */
    public Pickaxe() {
        super(ItemTypes.pickaxe, 1, ItemTexture.getPickaxeTexture(), new Mining());
    }

    @Override
    public void tick() {

    }
}
