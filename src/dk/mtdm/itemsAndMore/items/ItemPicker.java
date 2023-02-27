package dk.mtdm.itemsAndMore.items;

public class ItemPicker {
    public static Item picker(ItemTypes type) {
        return switch (type) {
            case stick: {
                yield new Stick();
            }
            case pickaxe: {
                yield new Pickaxe();
            }
            default: {
                yield null;
            }
        };
    }
}
