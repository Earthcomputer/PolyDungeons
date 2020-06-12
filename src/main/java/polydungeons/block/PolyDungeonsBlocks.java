package polydungeons.block;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import polydungeons.PolyDungeons;
import polydungeons.item.PolyDungeonsItems;

public class PolyDungeonsBlocks {
    public static HardenedBlock HARDENED_RED_NETHER_BRICKS;
    public static HardenedBlock HARDENED_CRACKED_RED_NETHER_BRICKS;

    private static <T extends Block> T registerBlock(T block, String id) {
        Registry.register(Registry.BLOCK, new Identifier(PolyDungeons.MODID, id), block);
        BlockItem itemBlock = new BlockItem(block, new Item.Settings());
        PolyDungeonsItems.registerItem(itemBlock, id);
        return block;
    }

    public static void registerAll() {

    }
}
