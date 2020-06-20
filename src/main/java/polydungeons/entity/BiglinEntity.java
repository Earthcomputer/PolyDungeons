package polydungeons.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class BiglinEntity extends HostileEntity {

	private final ServerBossBar bossBar;

	protected BiglinEntity(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
		this.setHealth(this.getMaxHealth());
		this.getNavigation().setCanSwim(true);

		this.bossBar = (ServerBossBar)(new ServerBossBar(
				this.getDisplayName(),
				BossBar.Color.PURPLE,
				BossBar.Style.PROGRESS)
		).setThickenFog(true);
	}

	public static DefaultAttributeContainer.Builder createBiglinAttributes() {
		return HostileEntity.createHostileAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 100.0d)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.5d)
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0d)
				;
	}
	@Override
	protected void initGoals() {
		this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 16.0f));
		this.goalSelector.add(6, new WanderAroundFarGoal(this, 1.0d));
		this.goalSelector.add(7, new LookAroundGoal(this));
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
	}
}
