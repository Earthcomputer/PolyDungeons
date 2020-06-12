package polydungeons.item;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;

@FunctionalInterface
public interface EnchantmentApplicableCallback {

    Event<EnchantmentApplicableCallback> EVENT = EventFactory.createArrayBacked(EnchantmentApplicableCallback.class, listeners -> (enchantment, stack, enchantmentTable) -> {
        for (EnchantmentApplicableCallback listener : listeners) {
            ActionResult result = listener.isApplicable(enchantment, stack, enchantmentTable);
            if (result != ActionResult.PASS) {
                return result;
            }
        }
        return ActionResult.PASS;
    });

    ActionResult isApplicable(Enchantment enchantment, ItemStack stack, boolean enchantmentTable);

}
