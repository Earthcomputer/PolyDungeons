package polydungeons.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import polydungeons.item.PolyDungeonsItems;
import polydungeons.network.PolyDungeonsServerNetwork;

import java.util.Random;

public class SpearEntity extends PersistentProjectileEntity {
	private ItemStack spearStack;
	private boolean dealtDamage;

	public float damage = 4f;

	public SpearEntity(EntityType<SpearEntity> entityType, World world) {
		super(entityType, world);
		this.spearStack = new ItemStack(PolyDungeonsItems.SPEAR);
	}

	public SpearEntity(World world, LivingEntity owner, ItemStack stack) {
		super(PolyDungeonsEntities.SPEAR, owner, world);
		this.spearStack = new ItemStack(PolyDungeonsItems.SPEAR);
		this.spearStack = stack.copy();
	}

	@Environment(EnvType.CLIENT)
	public SpearEntity(World world, double x, double y, double z) {
		super(PolyDungeonsEntities.SPEAR, x, y, z, world);
		this.spearStack = new ItemStack(PolyDungeonsItems.SPEAR);
	}

	public void tick() {
		if (this.inGroundTime > 4) {
			this.dealtDamage = true;
		}

		super.tick();
	}

	public SpearEntity setDamage(float damage) {
		this.damage = damage;
		return this;
	}

	private boolean isOwnerAlive() {
		Entity entity = this.getOwner();
		if (entity != null && entity.isAlive()) {
			return !(entity instanceof ServerPlayerEntity) || !entity.isSpectator();
		} else {
			return false;
		}
	}

	@Override
	protected ItemStack asItemStack() {
		return this.spearStack.copy();
	}

	@Override
	protected EntityHitResult getEntityCollision(Vec3d currentPosition, Vec3d nextPosition) {
		return this.dealtDamage ? null : super.getEntityCollision(currentPosition, nextPosition);
	}

	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		Entity entity = entityHitResult.getEntity();
		Random random = new Random();

		Entity entity2 = this.getOwner();
		DamageSource damageSource = DamageSource.trident(this, (Entity)(entity2 == null ? this : entity2));
		this.dealtDamage = true;
		SoundEvent soundEvent = SoundEvents.ITEM_TRIDENT_HIT;
		if (entity.damage(damageSource, damage)) {
			if (entity.getType() == EntityType.ENDERMAN) {
				return;
			}

			if (entity instanceof LivingEntity) {
				LivingEntity livingEntity2 = (LivingEntity)entity;
				if (entity2 instanceof LivingEntity) {
					EnchantmentHelper.onUserDamaged(livingEntity2, entity2);
					EnchantmentHelper.onTargetDamaged((LivingEntity)entity2, livingEntity2);
				}

				this.onHit(livingEntity2);
			}
		}

		this.playSound(soundEvent, 1.0f, 1.0f);
	}

	@Override
	protected void onBlockHit(BlockHitResult blockHitResult) {
		super.onBlockHit(blockHitResult);

	}

	@Override
	protected SoundEvent getHitSound() {
		return SoundEvents.ITEM_TRIDENT_HIT;
	}

	@Override
	public void onPlayerCollision(PlayerEntity player) {
		Entity entity = this.getOwner();
		if (entity == null || entity.getUuid() == player.getUuid()) {
			super.onPlayerCollision(player);
		}
	}
	@Override
	public Packet<?> createSpawnPacket() {
		return PolyDungeonsServerNetwork.spawnEntity(this, buf -> {
			Entity owner = getOwner();
			buf.writeVarInt(owner != null ? owner.getEntityId() : 0);
		});
	}
}
