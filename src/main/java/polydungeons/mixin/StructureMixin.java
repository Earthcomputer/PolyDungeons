package polydungeons.mixin;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Blocks;
import net.minecraft.structure.Structure;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import polydungeons.structures.DungeonData;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mixin(Structure.class)
public class StructureMixin {
    @Shadow private BlockPos size;

    @ModifyArg(method = "place(Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/structure/StructurePlacementData;Ljava/util/Random;I)Z",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/structure/Structure;process(Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/structure/StructurePlacementData;Ljava/util/List;)Ljava/util/List;"))
    private List<Structure.StructureBlockInfo> addStructureVoids(List<Structure.StructureBlockInfo> blockInfos) {
        if (!FabricLoader.getInstance().isDevelopmentEnvironment() || !DungeonData.placingByStructureBlock) {
            return blockInfos;
        }

        Set<BlockPos> existingPositions = blockInfos.stream().map(info -> info.pos).collect(Collectors.toSet());
        for (BlockPos pos : BlockPos.iterate(BlockPos.ORIGIN, size.add(-1, -1, -1))) {
            if (!existingPositions.contains(pos)) {
                blockInfos.add(new Structure.StructureBlockInfo(pos.toImmutable(), Blocks.STRUCTURE_VOID.getDefaultState(), null));
            }
        }

        return blockInfos;
    }
}
