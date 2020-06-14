package polydungeons.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import polydungeons.item.SlingshotItem;

@Mixin(HeldItemRenderer.class)
@Environment(EnvType.CLIENT)
public abstract class HeldItemRendererMixin {

    @Inject(method = "renderFirstPersonItem",
            slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getUseAction()Lnet/minecraft/util/UseAction;")),
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/HeldItemRenderer;renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", ordinal = 0))
    private void onRenderFirstPersonItem(AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        if (item.getItem() instanceof SlingshotItem) {
            if (player.isUsingItem() && player.getItemUseTimeLeft() > 0 && player.getActiveHand() == hand) {
                boolean usingMainHand = hand == Hand.MAIN_HAND;
                Arm arm = usingMainHand ? player.getMainArm() : player.getMainArm().getOpposite();
                boolean usingRightArm = arm == Arm.RIGHT;
                int handOffset = usingRightArm ? 1 : -1;
                matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(60 * handOffset));
                matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(-60 * handOffset));

                float ticksUsed = (float)item.getMaxUseTime() - (player.getItemUseTimeLeft() - tickDelta + 1.0F);
                float amountPulled = SlingshotItem.getPullProgress(ticksUsed);

                if (amountPulled > 0.1F) {
                    float shakeProgress = MathHelper.sin((ticksUsed - 0.1F) * 1.3F);
                    float shakeMangitude = amountPulled - 0.1F;
                    float shakeAmount = shakeProgress * shakeMangitude;
                    matrices.translate(shakeAmount * 0.0F, shakeAmount * 0.004F, shakeAmount * 0.0F);
                }

                matrices.translate(0, 0.2 - amountPulled * 0.2, amountPulled * 0.05);

                matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(-20f));
                matrices.scale(1, 1 + amountPulled * 0.3f, 1);
                matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(20f));
            }
        }
    }
}
