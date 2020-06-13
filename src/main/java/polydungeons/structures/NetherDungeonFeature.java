package polydungeons.structures;

import com.mojang.serialization.Codec;
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

public class NetherDungeonFeature extends StructureFeature<StructurePoolFeatureConfig> {
    public NetherDungeonFeature(Codec<StructurePoolFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public StructureStartFactory<StructurePoolFeatureConfig> getStructureStartFactory() {
        return Start::new;
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
