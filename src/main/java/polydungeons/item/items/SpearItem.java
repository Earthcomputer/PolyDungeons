package polydungeons.item.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import polydungeons.entity.SpearEntity;

import java.util.Random;

public class SpearItem extends Item {

	private final float attackDamage;
	private final Multimap<EntityAttribute, EntityAttributeModifier> modifiers;

	public SpearItem(Settings settings) {
		super(settings);
		this.attackDamage = 3.5f;
		ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", this.attackDamage, EntityAttributeModifier.Operation.ADDITION));
		this.modifiers = builder.build();
	}


	@Override
	public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
		shoot(stack, world, user, Math.min(0.8f, (this.getMaxUseTime(stack) - remainingUseTicks) * 0.1f), 3);
		if(user instanceof PlayerEntity)
			((PlayerEntity) user).incrementStat(Stats.USED.getOrCreateStat(this));
	}

	public static void shoot(ItemStack stack, World world, LivingEntity user, float power, float damage) {
		Random random = new Random();
		SpearEntity tridentEntity = new SpearEntity(world, user, new ItemStack(stack.getItem(), 1)).setDamage(damage + random.nextInt(4));
		tridentEntity.setProperties(user, user.pitch, user.yaw, 0.0f, power, 1.0f);

		if (user instanceof PlayerEntity) {
			PlayerEntity playerEntity = (PlayerEntity) user;
			if (playerEntity.abilities.creativeMode) {
				tridentEntity.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
			} else {
				stack.decrement(1);
			}

		} else {
			tridentEntity.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
		}

		world.spawnEntity(tridentEntity);
		world.playSoundFromEntity(null, tridentEntity, SoundEvents.ITEM_TRIDENT_THROW, SoundCategory.PLAYERS, 1.0f, 1.0f);
	}
	public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
		return slot == EquipmentSlot.MAINHAND ? this.modifiers : super.getAttributeModifiers(slot);
	}

	@Override
	public int getMaxUseTime(ItemStack stack) {
		return 72000;
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.SPEAR;
	}
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		user.setCurrentHand(hand);
		return TypedActionResult.consume(user.getStackInHand(hand));
	}
}
