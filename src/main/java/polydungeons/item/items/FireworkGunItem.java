package polydungeons.item.items;

import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.item.*;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.*;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import polydungeons.PolyDungeons;
import polydungeons.sound.PolyDungeonsSoundEvents;

import java.util.function.Predicate;

public class FireworkGunItem extends RangedWeaponItem implements Vanishable {
	public static final Identifier CONTAINER_ID = new Identifier(PolyDungeons.MODID, "firework_gun");

	public FireworkGunItem() {
		super(new Settings().rarity(Rarity.RARE).group(ItemGroup.COMBAT).maxCount(1));
	}
	@Override
	public Predicate<ItemStack> getProjectiles() {
		return (i) -> i.isItemEqual(new ItemStack(Items.FIREWORK_ROCKET));
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack stack = user.getStackInHand(hand);
		user.setCurrentHand(hand);
		return TypedActionResult.pass(stack);
	}

	@Override
	public int getMaxUseTime(ItemStack stack) {
		return Integer.MAX_VALUE;
	}

	public static void shoot(World world, LivingEntity shooter, Hand hand, ItemStack gun, ItemStack projectile, float soundPitch, float speed, float divergence, float simulated) {
		if (!world.isClient) {
			FireworkRocketEntity rocket = new FireworkRocketEntity(world, projectile, shooter, shooter.getX(), shooter.getEyeY() - 0.15000000596046448D, shooter.getZ(), true);

			Vec3d vec3d = shooter.getOppositeRotationVector(1.0F);
			Quaternion quaternion = new Quaternion(new Vector3f(vec3d), simulated, true);
			Vec3d vec3d2 = shooter.getRotationVec(1.0F);
			Vector3f vector3f = new Vector3f(vec3d2);
			vector3f.rotate(quaternion);
			(rocket).setVelocity(vector3f.getX(), vector3f.getY(), vector3f.getZ(), speed, divergence);

			world.spawnEntity(rocket);
			world.playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(), PolyDungeonsSoundEvents.SHOOT, SoundCategory.PLAYERS, 1.0F, soundPitch);
		}
	}

	@Override
	public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
		super.usageTick(world, user, stack, remainingUseTicks);
		if(remainingUseTicks % 2 == 0)
			shoot(world, user, user.getActiveHand(), user.getStackInHand(user.getActiveHand()), new ItemStack(Items.FIREWORK_ROCKET, 1), 1f, 2f, 0f, 0f);
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.BOW;
	}


	@Override
	public int getRange() {
		return 32;
	}
}
