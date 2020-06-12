package polydungeons.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;
import polydungeons.PolyDungeons;

public class PolyDungeonsItems {

    public static FireworkLauncherItem FIREWORK_LAUNCHER;
    public static SlingshotItem SLINGSHOT;

    public static AnchorItem ANCHOR;

    public static SpearItem SPEAR;
    public static AtlatlItem ATLATL;

    public static <T extends Item> T registerItem(T item, String id) {
        Registry.register(Registry.ITEM, new Identifier(PolyDungeons.MODID, id), item);
        return item;
    }

    public static void registerAll() {
        SLINGSHOT = registerItem(new SlingshotItem(new Item.Settings().maxDamage(384).group(ItemGroup.COMBAT)), "slingshot");
        FIREWORK_LAUNCHER = registerItem(new FireworkLauncherItem(new Item.Settings().maxCount(1).rarity(Rarity.RARE).group(ItemGroup.COMBAT)), "firework_launcher");

        SPEAR = registerItem(new SpearItem(new Item.Settings().group(ItemGroup.COMBAT)), "spear");
        ATLATL = registerItem(new AtlatlItem(new Item.Settings().maxCount(1).rarity(Rarity.RARE).group(ItemGroup.COMBAT)), "atlatl");
        ANCHOR = registerItem(new AnchorItem(), "anchor");
    }
}
