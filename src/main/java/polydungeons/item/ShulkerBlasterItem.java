package polydungeons.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.mob.GuardianEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ShulkerBulletEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;
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
		if (!world.isClient) {
			Random random = new Random();
			world.getClosestPlayer(user, 128);
//			Predicate<LivingEntity> predicate = EntityPredicates.VALID_LIVING_ENTITY;
			TargetPredicate predicate = new TargetPredicate().setPredicate(new Predicate<LivingEntity>() {
				@Override
				public boolean test(LivingEntity livingEntity) {
					return livingEntity.hurtTime > 20;
				}
			});
			Box box = new Box(
					user.getX() - 48, user.getY() - 48, user.getZ() - 48,
					user.getX() + 48, user.getY() + 48, user.getZ() + 48
			);
			LivingEntity entity = world.getClosestEntity(
					LivingEntity.class,
					predicate,
					user,
					user.getX(), user.getY(), user.getZ(),
					box
			);
//			List<Entity> entities = world.getEntities(user, box, predicate);

			world.spawnEntity(new ShulkerBulletEntity(world, user, entity, Direction.Axis.Y));
			user.playSound(SoundEvents.ENTITY_SHULKER_SHOOT, 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
		}
	}

	public Entity getTarget() {
		return null;
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

