package polydungeons.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import polydungeons.item.EnchantmentApplicableCallback;

import java.util.List;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {
    @Unique
    private static ThreadLocal<Enchantment> capturedEnchantment = new ThreadLocal<>();

    @ModifyVariable(method = "getPossibleEntries",
            ordinal = 0,
            slice = @Slice(from = @At(value = "INVOKE", target = "Ljava/util/Iterator;next()Ljava/lang/Object;")),
            at = @At(value = "STORE", ordinal = 0))
    private static Enchantment captureEnchantment(Enchantment enchantment) {
        capturedEnchantment.set(enchantment);
        return enchantment;
    }

    @Redirect(method = "getPossibleEntries", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentTarget;isAcceptableItem(Lnet/minecraft/item/Item;)Z"))
    private static boolean isAcceptableItem(EnchantmentTarget target, Item item, int power, ItemStack stack) {
        ActionResult result = EnchantmentApplicableCallback.EVENT.invoker().isApplicable(capturedEnchantment.get(), stack, true);
        if (result == ActionResult.PASS) {
            return target.isAcceptableItem(item);
        } else {
            return result.isAccepted();
        }
    }

    @Inject(method = "getPossibleEntries", at = @At("RETURN"))
    private static void clearCapturedEnchantment(CallbackInfoReturnable<List<EnchantmentLevelEntry>> ci) {
        capturedEnchantment.set(null);
    }
}
