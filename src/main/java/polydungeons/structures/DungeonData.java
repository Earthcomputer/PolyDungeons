package polydungeons.structures;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.earthcomputer.libstructure.LibStructure;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.earthcomputer.libstructure.LibStructure;
import net.minecraft.block.Blocks;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.structure.pool.SinglePoolElement;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.structure.processor.RuleStructureProcessor;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorRule;
import net.minecraft.structure.rule.AlwaysTrueRuleTest;
import net.minecraft.structure.rule.RandomBlockMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.chunk.StructureConfig;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.chunk.StructureConfig;
import polydungeons.PolyDungeons;

import java.util.Arrays;
import java.util.List;

public class DungeonData {

    public static final NetherDungeonFeature NETHER_DUNGEON = new NetherDungeonFeature(StructurePoolFeatureConfig.CODEC);
    public static final ConfiguredStructureFeature<StructurePoolFeatureConfig, ? extends StructureFeature<StructurePoolFeatureConfig>> NETHER_DUNGEON_FEATURE = NETHER_DUNGEON.configure(new StructurePoolFeatureConfig(new Identifier(PolyDungeons.MODID, "dungeon/rooms"), 8));
    public static final StructurePieceType NETHER_DUNGEON_PIECE = registerPieceType(NetherDungeonFeature.Piece::new, "nether_dungeon");

    private static StructurePieceType registerPieceType(StructurePieceType type, String id) {
        return Registry.register(Registry.STRUCTURE_PIECE, new Identifier(PolyDungeons.MODID, id), type);
    }

    private static void registerPool(String name, String terminators, Object... elementsAndCounts) {
        if (elementsAndCounts.length % 2 != 0) {
            throw new IllegalArgumentException("elementsAndCounts has an incorrect length!");
        }
        ImmutableList.Builder<Pair<StructurePoolElement, Integer>> elements = ImmutableList.builder();
        for (int i = 0; i < elementsAndCounts.length; i += 2) {
            elements.add(new Pair<>((StructurePoolElement) elementsAndCounts[i], (int)elementsAndCounts[i + 1]));
        }

        StructurePoolBasedGenerator.REGISTRY.add(
                new StructurePool(
                        new Identifier(PolyDungeons.MODID, name),
                        new Identifier(terminators),
                        elements.build(),
                        StructurePool.Projection.RIGID
                )
        );
    }

    @SuppressWarnings("deprecation")
    private static SinglePoolElement singlePool(String location, List<StructureProcessor> processors) {
        return new SinglePoolElement(PolyDungeons.MODID + ":" + location, processors);
    }

    public static void init() {
        LibStructure.registerStructureWithPool(
                new Identifier(PolyDungeons.MODID, "nether_dungeon"),
                NETHER_DUNGEON,
                GenerationStep.Feature.UNDERGROUND_DECORATION,
                new StructureConfig(32, 8, 12345),
                NETHER_DUNGEON_FEATURE
        );
        for (Biome biome : Registry.BIOME) {
            if (biome.getCategory() == Biome.Category.NETHER) {
                biome.addStructureFeature(NETHER_DUNGEON_FEATURE);
            }
        }
        RegistryEntryAddedCallback.event(Registry.BIOME).register((rawId, id, biome) -> {
            if (biome.getCategory() == Biome.Category.NETHER) {
                biome.addStructureFeature(NETHER_DUNGEON_FEATURE);
            }
        });
    }

    static {
        // it took me a while to figure out what exactly this does, but it turns out it randomly replaces blocks.
        // intuitive, right?
        ImmutableList<StructureProcessor> crackBlackstone = ImmutableList.of(
            new RuleStructureProcessor(
                    ImmutableList.of(
                            new StructureProcessorRule(
                                new RandomBlockMatchRuleTest(Blocks.POLISHED_BLACKSTONE_BRICKS, 0.1F),
                                AlwaysTrueRuleTest.INSTANCE,
                                Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS.getDefaultState()
                            ),
                            new StructureProcessorRule(
                                    new RandomBlockMatchRuleTest(Blocks.POLISHED_BLACKSTONE, 0.1F),
                                    AlwaysTrueRuleTest.INSTANCE,
                                    Blocks.BLACKSTONE.getDefaultState()
                            ),
                            new StructureProcessorRule(
                                    new RandomBlockMatchRuleTest(Blocks.SOUL_LANTERN, 0.1F),
                                    AlwaysTrueRuleTest.INSTANCE,
                                    Blocks.AIR.getDefaultState()
                            )
                    ))
        );
        registerPool("dungeon/caps", "empty",
                singlePool("dungeon/caps/cap", crackBlackstone), 1,
                singlePool("dungeon/caps/small_cap", crackBlackstone), 1
        );

        registerPool("dungeon/rooms", "dungeon/caps",
                singlePool("dungeon/rooms/room_7x7", crackBlackstone), 32,
                singlePool("dungeon/rooms/room_14x14", crackBlackstone), 16,
                singlePool("dungeon/rooms/room_collapsed", crackBlackstone), 16,
                singlePool("dungeon/rooms/room_corrupt", crackBlackstone), 1,
                singlePool("dungeon/rooms/staircase", crackBlackstone), 16,
                singlePool("dungeon/halls/hall_7x7", crackBlackstone), 48
        );

        registerPool("dungeon/room_features", "empty",
                singlePool("dungeon/room_features/soul_soil_pile", crackBlackstone), 1,
                singlePool("dungeon/room_features/loot_basic", crackBlackstone), 4,
                singlePool("dungeon/room_features/cage_room", crackBlackstone), 4,
                singlePool("dungeon/room_features/end_room", crackBlackstone), 1,
                singlePool("dungeon/room_features/nothing", crackBlackstone), 20
        );
    }
}
