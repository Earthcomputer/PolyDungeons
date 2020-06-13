package polydungeons.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import polydungeons.entity.AnchorEntity;
import polydungeons.entity.SplatEntity;

import java.util.List;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    public abstract void setHealth(float health);

    @Shadow
    public abstract boolean clearStatusEffects();

    @Shadow
    protected boolean dead;

    @Shadow public abstract EntityAttributeInstance getAttributeInstance(EntityAttribute attribute);

    @Inject(method = "baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;shouldDisplaySoulSpeedEffects()Z", ordinal = 0))
    private void onTick(CallbackInfo ci) {
        if (!world.isClient || isLogicalSideForUpdatingMovement()) {
            List<SplatEntity> splats = world.getEntities(SplatEntity.class, getBoundingBox(), EntityPredicates.VALID_ENTITY);
            EntityAttributeInstance movementSpeed = getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
            if (splats.isEmpty()) {
                if (movementSpeed != null) {
                    movementSpeed.removeModifier(SplatEntity.SPLAT_SLOWDOWN_UUID);
                }
            } else {
                if (movementSpeed != null) {
                    EntityAttributeModifier modifier = new EntityAttributeModifier(SplatEntity.SPLAT_SLOWDOWN_UUID, "Splat Slowdown", -0.5, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
                    if (!movementSpeed.hasModifier(modifier)) {
                        movementSpeed.addTemporaryModifier(modifier);
                    }
                }
                if (splats.stream().anyMatch(SplatEntity::isFiery)) {
                    setOnFireFor(2);
                }
            }
        }
    }

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
