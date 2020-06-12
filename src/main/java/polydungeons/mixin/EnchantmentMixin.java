package polydungeons.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import polydungeons.item.EnchantmentApplicableCallback;

@Mixin(Enchantment.class)
public class EnchantmentMixin {
    @Inject(method = "isAcceptableItem", at = @At("RETURN"), cancellable = true)
    private void fireEnchantmentApplicableEvent(ItemStack stack, CallbackInfoReturnable<Boolean> ci) {
        ActionResult result = EnchantmentApplicableCallback.EVENT.invoker().isApplicable((Enchantment) (Object) this, stack, false);
        if (result != ActionResult.PASS) {
            ci.setReturnValue(result.isAccepted());
        }
    }
}
