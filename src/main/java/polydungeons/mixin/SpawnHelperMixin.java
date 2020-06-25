package polydungeons.mixin;

import net.minecraft.entity.SpawnGroup;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import polydungeons.structures.DungeonData;
import polydungeons.tag.PolyDungeonsBlockTags;

import java.util.List;

@Mixin(SpawnHelper.class)
public class SpawnHelperMixin {
    @Inject(method = "method_29950", at = @At("HEAD"), cancellable = true)
    private static void addNetherDungeonSpawnEntries(ServerWorld world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, SpawnGroup spawnGroup, BlockPos pos, Biome biome, CallbackInfoReturnable<List<Biome.SpawnEntry>> ci) {
        if (spawnGroup == SpawnGroup.MONSTER
                && world.getBlockState(pos.down()).getBlock().isIn(PolyDungeonsBlockTags.NETHER_DUNGEON_SPAWNABLE_BLOCKS)
                && structureAccessor.method_28388(pos, false, DungeonData.NETHER_DUNGEON).hasChildren()) {
            ci.setReturnValue(DungeonData.NETHER_DUNGEON.getMonsterSpawns());
        }
    }
}
