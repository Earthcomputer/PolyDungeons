package polydungeons.tag;

import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import polydungeons.PolyDungeons;

public class PolyDungeonsBlockTags {
    public static final Tag<Block> NETHER_DUNGEON_SPAWNABLE_BLOCKS = TagRegistry.block(new Identifier(PolyDungeons.MODID, "nether_dungeon_spawnable_blocks"));

    public static void register() {
        // load class
    }
}
