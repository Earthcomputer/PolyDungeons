package polydungeons.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import polydungeons.entity.AnchorEntity;

@Mixin(LivingEntity.class)
public abstract class PlayerTotemMixin extends Entity {
    public PlayerTotemMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    public abstract void setHealth(float health);

    @Shadow
    public abstract boolean clearStatusEffects();

    @Shadow
    protected boolean dead;

    @Inject(method = "tryUseTotem(Lnet/minecraft/entity/damage/DamageSource;)Z", at = @At("HEAD"), cancellable = true)
    private void tryUseTotem(DamageSource source, CallbackInfoReturnable<Boolean> info) {
        for(AnchorEntity anchor : AnchorEntity.anchorsInWorld) {
            if(anchor.getEntityWorld().getDimension() == getEntityWorld().getDimension()) {
                if (anchor.getPos().distanceTo(this.getPos()) <= AnchorEntity.RADIUS) {
                    Vec3d anchorPos = anchor.getPos();
                    this.requestTeleport(anchorPos.x, anchorPos.y - 1.5, anchorPos.z);

                    info.setReturnValue(true);
                    this.setHealth(1.0F);
                    this.clearStatusEffects();
                    dead = false;


                    AnchorEntity.anchorsInWorld.remove(anchor);
                    anchor.kill();
                    return;
                }
            }
        }
    }
}
