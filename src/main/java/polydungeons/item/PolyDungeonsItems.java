package polydungeons.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import polydungeons.PolyDungeons;

public class PolyDungeonsItems {

    public static FireworkGunItem FIREWORK_GUN;

    public static SlingshotItem SLINGSHOT;

    public static AnchorItem ANCHOR;

    public static <T extends Item> T registerItem(T item, String id) {
        Registry.register(Registry.ITEM, new Identifier(PolyDungeons.MODID, id), item);
        return item;
    }

    public static void registerAll() {
        SLINGSHOT = registerItem(new SlingshotItem(new Item.Settings().maxDamage(384).group(ItemGroup.COMBAT)), "slingshot");
        FIREWORK_GUN = registerItem(new FireworkGunItem(), "firework_gun");
        ANCHOR = registerItem(new AnchorItem(), "anchor");
    }
}
