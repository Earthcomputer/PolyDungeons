package polydungeons.mixin;

import net.minecraft.block.entity.StructureBlockBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import polydungeons.structures.DungeonData;

@Mixin(StructureBlockBlockEntity.class)
public class StructureBlockBlockEntityMixin {
    @Inject(method = "place", at = @At(value = "INVOKE", target = "Lnet/minecraft/structure/Structure;place(Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/structure/StructurePlacementData;Ljava/util/Random;)V"))
    private void prePlace(CallbackInfoReturnable<Boolean> ci) {
        DungeonData.placingByStructureBlock = true;
    }

    @Inject(method = "place", at = @At(value = "INVOKE", target = "Lnet/minecraft/structure/Structure;place(Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/structure/StructurePlacementData;Ljava/util/Random;)V", shift = At.Shift.AFTER))
    private void postPlace(CallbackInfoReturnable<Boolean> ci) {
        DungeonData.placingByStructureBlock = false;
    }
}
