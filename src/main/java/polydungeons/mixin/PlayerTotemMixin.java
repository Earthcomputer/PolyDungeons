package polydungeons.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import polydungeons.entity.AnchorEntity;
import polydungeons.entity.PolyDungeonsEntities;

import java.util.List;

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
        //noinspection ConstantConditions
        //if(source.getSource().getEntityWorld().isClient) return;
        if((Object)this instanceof PlayerEntity) {
            List<AnchorEntity> entities = this.getEntityWorld().getEntities(
                    PolyDungeonsEntities.ANCHOR,
                    new Box(this.getPos().subtract(AnchorEntity.RADIUS, AnchorEntity.RADIUS, AnchorEntity.RADIUS),
                            this.getPos().add(AnchorEntity.RADIUS, AnchorEntity.RADIUS, AnchorEntity.RADIUS)),
                    anchor -> true
            );
            System.out.println(entities);
            for (AnchorEntity anchor : entities) {
                System.out.println("Found anchor.");
                Vec3d anchorPos = anchor.getPos();
                this.requestTeleport(anchorPos.x, anchorPos.y - 1.5, anchorPos.z);

                info.setReturnValue(true);
                this.setHealth(1.0F);
                this.clearStatusEffects();
                dead = false;

                anchor.kill();
                return;
            }
        }
    }
}
