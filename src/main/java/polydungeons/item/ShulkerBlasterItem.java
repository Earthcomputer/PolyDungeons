package polydungeons.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ShulkerBulletEntity;
import net.minecraft.item.*;
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

public class ShulkerBlasterItem extends RangedWeaponItem implements Vanishable {

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
		if(remainingUseTicks % 10 == 0) {
			if (!world.isClient) {
				if (user instanceof PlayerEntity) {
					if(((PlayerEntity) user).isCreative()) {
						shoot(world, (PlayerEntity) user, stack);
					} else {
						ItemStack type = user.getArrowType(stack);
						if(!type.isEmpty()) {
							if (shoot(world, (PlayerEntity) user, stack)) {
								type.decrement((int) (Math.random() * 2));
							}
						}
					}
				}
			}
		}
	}
	public boolean shoot(World world, PlayerEntity player, ItemStack stack) {
		Box box = new Box(
				player.getX() - 24, player.getY() - 24, player.getZ() - 24,
				player.getX() + 24, player.getY() + 24, player.getZ() + 24
		);
		List<LivingEntity> entities = world.getEntities(LivingEntity.class, box, entity -> entity != player);
		if (entities.isEmpty()) {
			return false;
		}

		LivingEntity target = getTarget(player, entities);
		((ILivingEntity) target).incrementTimesTargeted();
		ShulkerBulletEntity entity = new ShulkerBulletEntity(world, player, target, Direction.Axis.Y);
		entity.updatePosition(player.getX(), player.getY() + 1, player.getZ());

		Vec3d playerRotation = player.getRotationVector();

		entity.addVelocity(playerRotation.x, playerRotation.y, playerRotation.z);
		world.spawnEntity(entity);
		world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_SHULKER_SHOOT, SoundCategory.PLAYERS, 1.0f, 1.0f);

		return true;
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
		return itemStack -> itemStack.isItemEqual(new ItemStack(Items.POPPED_CHORUS_FRUIT));
	}

	@Override
	public int getRange() {
		return 69;
	}

}

