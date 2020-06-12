package polydungeons.structures;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.Blocks;
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
import polydungeons.PolyDungeons;

import java.util.Arrays;
import java.util.List;

public class DungeonData {

    public static void registerPool(String name, String terminators, List<Pair<StructurePoolElement, Integer>> elements) {
        StructurePoolBasedGenerator.REGISTRY.add(
                new StructurePool(
                        new Identifier(PolyDungeons.MODID, name),
                        new Identifier(terminators),
                        elements,
                        StructurePool.Projection.RIGID
                )
        );
    }

    public static void init() {
    }

    static {
        // it took me a while to figure out what exactly this does, but it turns out it randomly replaces blocks.
        // intuitive, right?
        ImmutableList<StructureProcessor> crackBlackstone = ImmutableList.of(
            new RuleStructureProcessor(
                        ImmutableList.of(new StructureProcessorRule(
                                    new RandomBlockMatchRuleTest(Blocks.POLISHED_BLACKSTONE, 0.1F),
                                    AlwaysTrueRuleTest.INSTANCE,
                                    Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS.getDefaultState()
                        )))
        );

        registerPool("dungeon/rooms", "empty", Arrays.asList(
                new Pair<>(new SinglePoolElement("dungeon/rooms/5x5_chest", crackBlackstone), 1)
        ));

        registerPool("dungeon/halls", "empty", Arrays.asList(
                new Pair<>(new SinglePoolElement("dungeon/halls/10_3x3", crackBlackstone), 1)
        ));
    }
}
