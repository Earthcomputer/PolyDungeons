package polydungeons.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import polydungeons.client.entity.ILivingEntity;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements ILivingEntity {

	@Shadow public abstract float getMaxHealth();
	@Shadow public abstract float getHealth();

	@Unique private int timesTargeted = 1;
	@Unique private float targetPriority = 0;
	@Unique private boolean dirty = true;

	@Unique private PlayerEntity player;

	public LivingEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	private void recalculatePriority(PlayerEntity player, float maxHealth, float health, int timesTargeted, float distance) {
		this.targetPriority = maxHealth * (health / maxHealth) / timesTargeted / distance + maxHealth;
	}

	@Override
	public int getTimesTargeted() {
		return timesTargeted;
	}

	@Override
	public float getTargetPriority(PlayerEntity player) {
		if(this.player != player) {
			this.player = player;
			markDirty();
		}
		if(this.dirty) {
			recalculatePriority(player, this.getMaxHealth(), this.getMaxHealth() - this.getHealth() + 1, this.timesTargeted, this.distanceTo(player));
		}
		return this.targetPriority;
	}

	@Override
	public ILivingEntity setTimesHit(int timesHit) {
		this.timesTargeted = timesHit;
		this.markDirty();
		return this;
	}

	@Override
	public ILivingEntity incrementTimesTargeted() {
		this.timesTargeted++;
		this.markDirty();
		return this;
	}

	@Override
	public ILivingEntity markDirty() {
		this.dirty = true;
		return this;
	}
}
