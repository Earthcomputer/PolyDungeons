package polydungeons.block.entity;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import polydungeons.PolyDungeons;
import polydungeons.block.PolyDungeonsBlocks;
import polydungeons.block.entity.relic.SamplerRelicBlockEntity;

public class PolyDungeonsBlockEntities {
    public static BlockEntityType<SamplerRelicBlockEntity> SAMPLER_RELIC;
    public static BlockEntityType<DecorativeEndBlockEntity> END_BLOCK;

    private static <T extends BlockEntityType<?>> T register(T type, String name) {
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(PolyDungeons.MODID, name), type);
    }

    public static void registerAll() {
        SAMPLER_RELIC = register(
                BlockEntityType.Builder.create(SamplerRelicBlockEntity::new, PolyDungeonsBlocks.SAMPLER_RELIC).build(null),
                "sampler_relic"
        );

        END_BLOCK = register(
                BlockEntityType.Builder.create(DecorativeEndBlockEntity::new, PolyDungeonsBlocks.END_BLOCK).build(null),
                "end_block"
        );
    }
}
