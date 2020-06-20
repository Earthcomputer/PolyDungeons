package polydungeons.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ShulkerBulletEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import polydungeons.entity.ILivingEntity;

import java.util.List;
import java.util.function.Predicate;

public class ShulkerBlasterItem extends RangedWeaponItem {

	public ShulkerBlasterItem(Item.Settings settings) {
		super(settings);
	}

	@Override
	public int getMaxUseTime(ItemStack stack) {
		return 72000;
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		user.setCurrentHand(hand);
		return TypedActionResult.consume(user.getStackInHand(hand));
	}

	@Override
	public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
		super.usageTick(world, user, stack, remainingUseTicks);
		if(remainingUseTicks % 4 == 0) {
			if (!world.isClient) {
				if (user instanceof PlayerEntity) {
					shoot(world, (PlayerEntity)user, stack);
				}
			}
		}
	}
	public void shoot(World world, PlayerEntity player, ItemStack stack) {
		Box box = new Box(
				player.getX() - 24, player.getY() - 24, player.getZ() - 24,
				player.getX() + 24, player.getY() + 24, player.getZ() + 24
		);
		List<LivingEntity> entities = world.getEntities(LivingEntity.class, box, (Predicate<Entity>) entity -> {
			if (entity instanceof PlayerEntity)
				return false;
			if (entity instanceof LivingEntity)
				return true;
			return false;
		});
		if (!entities.isEmpty()) {
			LivingEntity target = getTarget(player, entities);
			((ILivingEntity) target).incrementTimesTargeted();
			ShulkerBulletEntity entity = new ShulkerBulletEntity(world, player, target, Direction.Axis.Y);
			entity.setPos(player.getX(), player.getY() + 1, player.getZ());

			Vec3d playerRotation = player.getRotationVector();

			entity.addVelocity(playerRotation.x, playerRotation.y, playerRotation.z);
			world.spawnEntity(entity);
			world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_SHULKER_SHOOT, SoundCategory.PLAYERS, 1.0f, 1.0f);
		}
	}

	public LivingEntity getTarget(PlayerEntity player, List<LivingEntity> entities) {
		LivingEntity ret = entities.get(0);
		float max = Float.MIN_VALUE;

		for(LivingEntity entity : entities) {
			float priority = ((ILivingEntity) entity).getTargetPriority(player);
			if(priority > max) {
				ret = entity;
				max = priority;
			}

		}
		return ret;
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.CROSSBOW;
	}

	@Override
	public Predicate<ItemStack> getProjectiles() {
		return itemStack -> itemStack.isItemEqual(new ItemStack(Items.CHORUS_FRUIT));
	}

	@Override
	public int getRange() {
		return 69;
	}

}

