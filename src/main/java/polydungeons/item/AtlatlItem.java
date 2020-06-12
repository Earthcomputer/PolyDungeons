package polydungeons.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

import java.util.function.Predicate;

public class AtlatlItem extends RangedWeaponItem {

	public AtlatlItem(Settings settings) {
		super(settings);
	}

	@Override
	public int getMaxUseTime(ItemStack stack) {
		return 72000;
	}

	@Override
	public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
		if(user instanceof PlayerEntity) {
			PlayerEntity playerEntity = (PlayerEntity) user;
			SpearItem.shoot(playerEntity.getArrowType(stack), world, user, Math.min(2f, (this.getMaxUseTime(stack) - remainingUseTicks) * 0.15f), 5);
			playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
		}
	}
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		user.setCurrentHand(hand);
		return TypedActionResult.consume(user.getStackInHand(hand));
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.SPEAR;
	}

	@Override
	public Predicate<ItemStack> getProjectiles() {
		return itemStack -> itemStack.isItemEqual(new ItemStack(PolyDungeonsItems.SPEAR));
	}

	@Override
	public int getRange() {
		return 69;
	}

}

