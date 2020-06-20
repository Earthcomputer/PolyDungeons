package polydungeons.mixin;

import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import polydungeons.entity.IMobEntity;

@Mixin(MobEntity.class)
public class MobEntityMixin implements IMobEntity {

    @Inject(method = "tickNewAi", at = @At(value = "INVOKE_STRING", target = "Lnet/minecraft/util/profiler/Profiler;push(Ljava/lang/String;)V", args = "ldc=controls", shift = At.Shift.AFTER))
    private void onControlTick(CallbackInfo ci) {
        polydungeons_onControlTick();
    }

}
