package polydungeons.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import polydungeons.item.SlingshotItem;

@Mixin(PlayerEntityRenderer.class)
@Environment(EnvType.CLIENT)
public class PlayerEntityRendererMixin {
    @Inject(method = "getArmPose", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getUseAction()Lnet/minecraft/util/UseAction;"), cancellable = true)
    private static void onGetArmPose(AbstractClientPlayerEntity player, Hand hand, CallbackInfoReturnable<BipedEntityModel.ArmPose> ci) {
        ItemStack stack = player.getStackInHand(hand);
        if (stack.getItem() instanceof SlingshotItem) {
            ci.setReturnValue(BipedEntityModel.ArmPose.BOW_AND_ARROW);
        }
    }
}
