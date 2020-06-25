package polydungeons.structures;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.VillageStructureStart;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;
import polydungeons.entity.PolyDungeonsEntities;

import java.util.List;

public class NetherDungeonFeature extends StructureFeature<StructurePoolFeatureConfig> {

    private static final List<Biome.SpawnEntry> MONSTER_SPAWNS = ImmutableList.of(
            new Biome.SpawnEntry(EntityType.PIGLIN, 1, 1, 3),
            new Biome.SpawnEntry(EntityType.ZOMBIFIED_PIGLIN, 1, 1, 3),
            new Biome.SpawnEntry(EntityType.BLAZE, 10, 1, 4),
            new Biome.SpawnEntry(EntityType.WITHER_SKELETON, 6, 1, 4),
            new Biome.SpawnEntry(PolyDungeonsEntities.BLAZING_MECHANICAL, 10, 1, 4),
            new Biome.SpawnEntry(PolyDungeonsEntities.SLUG_SLIME, 6, 1, 1)
    );

    public NetherDungeonFeature(Codec<StructurePoolFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public StructureStartFactory<StructurePoolFeatureConfig> getStructureStartFactory() {
        return Start::new;
    }

    @Override
    public List<Biome.SpawnEntry> getMonsterSpawns() {
        return MONSTER_SPAWNS;
    }

    public static class Start extends VillageStructureStart<StructurePoolFeatureConfig> {
        public Start(StructureFeature<StructurePoolFeatureConfig> structureFeature, int i, int j, BlockBox blockBox, int k, long l) {
            super(structureFeature, i, j, blockBox, k, l);
        }

        @Override
        public void init(ChunkGenerator chunkGenerator, StructureManager structureManager, int x, int z, Biome biome,
                         StructurePoolFeatureConfig config) {
            BlockPos startPos = new BlockPos(x * 16, 32, z * 16);
            StructurePoolBasedGenerator.addPieces(config.startPool, config.size, Piece::new, chunkGenerator, structureManager, startPos, children, random, false, false);
            setBoundingBoxFromChildren();
        }
    }

    public static class Piece extends PoolStructurePiece {
        public Piece(StructureManager manager, StructurePoolElement poolElement, BlockPos pos, int groundLevelDelta, BlockRotation rotation, BlockBox boundingBox) {
            super(DungeonData.NETHER_DUNGEON_PIECE, manager, poolElement, pos, groundLevelDelta, rotation, boundingBox);
        }

        public Piece(StructureManager manager, CompoundTag tag) {
            super(manager, tag, DungeonData.NETHER_DUNGEON_PIECE);
        }
    }
}
