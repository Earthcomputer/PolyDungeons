package polydungeons.item;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import polydungeons.PolyDungeons;
import polydungeons.item.items.FireworkGunItem;

public class PolyDungeonsItems {

    public static FireworkGunItem FIREWORK_GUN = new FireworkGunItem();

    public static <T extends Item> T registerItem(T item, String id) {
        Registry.register(Registry.ITEM, new Identifier(PolyDungeons.MODID, id), item);
        return item; }

    public static void registerAll() {
        registerItem(FIREWORK_GUN, "firework_gun");

    }
}
