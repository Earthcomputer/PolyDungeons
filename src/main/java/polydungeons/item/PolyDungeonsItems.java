package polydungeons.item;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;
import polydungeons.PolyDungeons;
import polydungeons.entity.PolyDungeonsEntities;
import polydungeons.item.charms.AnchorCharmItem;
import polydungeons.item.charms.SubstituteCharmItem;

public class PolyDungeonsItems {

    //public static EssenceItem SLIMY_ESSENCE;

    //public static FireworkLauncherItem FIREWORK_LAUNCHER;
    public static ShulkerBlasterItem SHULKER_BLASTER;
    public static SlingshotItem SLINGSHOT;

    public static AnchorCharmItem ANCHOR;
    public static SubstituteCharmItem SUBSTITUTE;

    public static SpearItem SPEAR;
    public static AtlatlItem ATLATL;

    public static JigsawDebugItem JIGSAW_DEBUG;

    public static SpawnEggItem BIGLIN_SPAWN_EGG;
    public static SpawnEggItem BLAZING_MECHANICAL_SPAWN_EGG;
    public static SpawnEggItem SLUG_SLIME_SPAWN_EGG;

    public static final ItemGroup GROUP = FabricItemGroupBuilder.create(new Identifier(PolyDungeons.MODID, PolyDungeons.MODID))
            .icon(() -> new ItemStack(SLINGSHOT)) // TODO: better icon?
            .build();

    public static <T extends Item> T registerItem(T item, String id) {
        Registry.register(Registry.ITEM, new Identifier(PolyDungeons.MODID, id), item);
        return item;
    }

    public static void registerAll() {
        //SLIMY_ESSENCE = registerItem(new EssenceItem(new Item.Settings().maxCount(16).rarity(Rarity.RARE).group(GROUP)), "slimy_essence");

        //FIREWORK_LAUNCHER = registerItem(new FireworkLauncherItem(new Item.Settings().maxCount(1).rarity(Rarity.RARE).group(GROUP)), "firework_launcher");
        SHULKER_BLASTER = registerItem(new ShulkerBlasterItem(new Item.Settings().maxCount(1).rarity(Rarity.EPIC).group(GROUP)), "shulker_blaster");
        SLINGSHOT = registerItem(new SlingshotItem(new Item.Settings().maxDamage(384).group(GROUP)), "slingshot");

        SPEAR = registerItem(new SpearItem(new Item.Settings().group(GROUP)), "spear");
        ATLATL = registerItem(new AtlatlItem(new Item.Settings().maxCount(1).rarity(Rarity.RARE).group(GROUP)), "atlatl");

        ANCHOR = registerItem(new AnchorCharmItem(), "anchor");
        SUBSTITUTE = registerItem(new SubstituteCharmItem(), "substitute");

        JIGSAW_DEBUG = registerItem(new JigsawDebugItem(), "jigsaw_debug_stick");

        //BIGLIN_SPAWN_EGG = registerItem(new SpawnEggItem(PolyDungeonsEntities.BIGLIN, 0x804f34, 0xd9d390, new Item.Settings().group(ItemGroup.MISC)), "biglin_spawn_egg");
        BLAZING_MECHANICAL_SPAWN_EGG = registerItem(new SpawnEggItem(PolyDungeonsEntities.BLAZING_MECHANICAL, 0xf6b201, 0x340000, new Item.Settings().group(ItemGroup.MISC)), "blazing_mechanical_spawn_egg");
        SLUG_SLIME_SPAWN_EGG = registerItem(new SpawnEggItem(PolyDungeonsEntities.SLUG_SLIME, 0x51a03e, 0xfff87e, new Item.Settings().group(ItemGroup.MISC)), "slug_slime_spawn_egg");
    }
}
