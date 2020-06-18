package polydungeons.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.control.LookControl;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntityWithAi;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class SlugSlimeEntity extends MobEntityWithAi implements IMobEntity {
    private static final TrackedData<Float> LEFT_EYE_YAW = DataTracker.registerData(SlugSlimeEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private float prevLeftEyeYaw;
    private static final TrackedData<Float> LEFT_EYE_PITCH = DataTracker.registerData(SlugSlimeEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private float prevLeftEyePitch;
    private static final TrackedData<Float> RIGHT_EYE_YAW = DataTracker.registerData(SlugSlimeEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private float prevRightEyeYaw;
    private static final TrackedData<Float> RIGHT_EYE_PITCH = DataTracker.registerData(SlugSlimeEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private float prevRightEyePitch;
    private static final TrackedData<Float> SNOUT_YAW = DataTracker.registerData(SlugSlimeEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private float prevSnoutYaw;
    private static final TrackedData<Float> SNOUT_PITCH = DataTracker.registerData(SlugSlimeEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private float prevSnoutPitch;

    private LookControl snoutLookControl;

    protected SlugSlimeEntity(EntityType<? extends MobEntityWithAi> entityType, World world) {
        super(entityType, world);
        lookControl = new SlugSlimeLookControl(this);
        snoutLookControl = new SnoutLookControl(this);
    }

    public SlugSlimeEntity(World world) {
        this(PolyDungeonsEntities.SLUG_SLIME, world);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        dataTracker.startTracking(LEFT_EYE_YAW, 0f);
        dataTracker.startTracking(LEFT_EYE_PITCH, 0f);
        dataTracker.startTracking(RIGHT_EYE_YAW, 0f);
        dataTracker.startTracking(RIGHT_EYE_PITCH, 0f);
        dataTracker.startTracking(SNOUT_YAW, 0f);
        dataTracker.startTracking(SNOUT_PITCH, 0f);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        goalSelector.add(5, new WanderAroundFarGoal(this, 1));
        goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8));
    }

    public static DefaultAttributeContainer.Builder createSlugSlimeAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.23);
    }

    @Override
    public void baseTick() {
        prevLeftEyeYaw = getLeftEyeYaw();
        prevLeftEyePitch = getLeftEyePitch();
        prevRightEyeYaw = getRightEyeYaw();
        prevRightEyePitch = getRightEyePitch();
        prevSnoutYaw = getSnoutYaw();
        prevSnoutPitch = getSnoutPitch();

        super.baseTick();
    }

    @Override
    public void polydungeons_onControlTick() {
        world.getProfiler().push("slugSlimeSnoutLook");
        snoutLookControl.tick();
        world.getProfiler().pop();
    }

    public float getLeftEyeYaw() {
        return dataTracker.get(LEFT_EYE_YAW);
    }

    public void setLeftEyeYaw(float leftEyeYaw) {
        dataTracker.set(LEFT_EYE_YAW, leftEyeYaw);
    }

    public float getLeftEyePitch() {
        return dataTracker.get(LEFT_EYE_PITCH);
    }

    public void setLeftEyePitch(float leftEyePitch) {
        dataTracker.set(LEFT_EYE_PITCH, leftEyePitch);
    }

    public float getRightEyeYaw() {
        return dataTracker.get(RIGHT_EYE_YAW);
    }

    public void setRightEyeYaw(float rightEyeYaw) {
        dataTracker.set(RIGHT_EYE_YAW, rightEyeYaw);
    }

    public float getRightEyePitch() {
        return dataTracker.get(RIGHT_EYE_PITCH);
    }

    public void setRightEyePitch(float rightEyePitch) {
        dataTracker.set(RIGHT_EYE_PITCH, rightEyePitch);
    }

    public float getSnoutYaw() {
        return dataTracker.get(SNOUT_YAW);
    }

    public void setSnoutYaw(float snoutYaw) {
        dataTracker.set(SNOUT_YAW, snoutYaw);
    }

    public float getSnoutPitch() {
        return dataTracker.get(SNOUT_PITCH);
    }

    public void setSnoutPitch(float snoutPitch) {
        dataTracker.set(SNOUT_PITCH, snoutPitch);
    }

    @Environment(EnvType.CLIENT)
    public float getLeftEyeYaw(float tickDelta) {
        return MathHelper.lerpAngleDegrees(tickDelta, prevLeftEyeYaw, getLeftEyeYaw());
    }

    @Environment(EnvType.CLIENT)
    public float getLeftEyePitch(float tickDelta) {
        return MathHelper.lerpAngleDegrees(tickDelta, prevLeftEyePitch, getLeftEyePitch());
    }

    @Environment(EnvType.CLIENT)
    public float getRightEyeYaw(float tickDelta) {
        return MathHelper.lerpAngleDegrees(tickDelta, prevRightEyeYaw, getRightEyeYaw());
    }

    @Environment(EnvType.CLIENT)
    public float getRightEyePitch(float tickDelta) {
        return MathHelper.lerpAngleDegrees(tickDelta, prevRightEyePitch, getRightEyePitch());
    }

    @Environment(EnvType.CLIENT)
    public float getSnoutYaw(float tickDelta) {
        return MathHelper.lerpAngleDegrees(tickDelta, prevSnoutYaw, getSnoutYaw());
    }

    @Environment(EnvType.CLIENT)
    public float getSnoutPitch(float tickDelta) {
        return MathHelper.lerpAngleDegrees(tickDelta, prevSnoutPitch, getSnoutPitch());
    }

    private static class SlugSlimeLookControl extends LookControl {
        private SlugSlimeEntity entity;
        private boolean movingLeftEye;
        private boolean movingRightEye;

        public SlugSlimeLookControl(SlugSlimeEntity entity) {
            super(entity);
            this.entity = entity;
        }

        @Override
        public void tick() {
            if (shouldStayHorizontal()) {
                entity.pitch = 0;
            }

            float targetYaw;
            float targetPitch;
            if (active) {
                active = false;
                targetYaw = getTargetYaw();
                targetPitch = getTargetPitch();
            } else {
                targetYaw = entity.bodyYaw;
                targetPitch = 0;
            }

            if (entity.getLeftEyeYaw() != targetYaw || entity.getLeftEyePitch() != targetPitch) {
                if (!movingLeftEye) {
                    movingLeftEye = entity.random.nextInt(10) == 0;
                }
                if (movingLeftEye) {
                    entity.setLeftEyeYaw(changeAngle(entity.getLeftEyeYaw(), targetYaw, yawSpeed));
                    entity.setLeftEyePitch(changeAngle(entity.getLeftEyePitch(), targetPitch, pitchSpeed));
                }
            } else {
                movingLeftEye = false;
            }

            if (entity.getRightEyeYaw() != targetYaw || entity.getRightEyePitch() != targetPitch) {
                if (!movingRightEye) {
                    movingRightEye = entity.random.nextInt(10) == 0;
                }
                if (movingRightEye) {
                    entity.setRightEyeYaw(changeAngle(entity.getRightEyeYaw(), targetYaw, yawSpeed));
                    entity.setRightEyePitch(changeAngle(entity.getRightEyePitch(), targetPitch, pitchSpeed));
                }
            } else {
                movingRightEye = false;
            }

            if (!entity.getNavigation().isIdle()) {
                entity.headYaw = MathHelper.stepAngleTowards(entity.headYaw, entity.bodyYaw, entity.getBodyYawSpeed());
            }
        }
    }

    private static class SnoutLookControl extends LookControl {
        private SlugSlimeEntity entity;

        public SnoutLookControl(SlugSlimeEntity entity) {
            super(entity);
            this.entity = entity;
        }

        @Override
        public void tick() {
            if (shouldStayHorizontal()) {
                entity.setSnoutPitch(0);
            }

            if (active) {
                active = false;
                entity.setSnoutYaw(changeAngle(entity.getSnoutYaw(), getTargetYaw(), yawSpeed));
                entity.setSnoutPitch(changeAngle(entity.getSnoutPitch(), getTargetPitch(), pitchSpeed));
            } else {
                final double TAU = 2 * Math.PI;
                final float YAW_RATE = 0.03f / (float) Math.PI;
                float targetYaw = entity.bodyYaw + 30 * (float)Math.sin(entity.age * YAW_RATE * TAU);
                final float PITCH_RATE = 0.03f;
                float targetPitch = entity.pitch - 45 - 15 * (float)Math.sin(entity.age * PITCH_RATE * TAU - 0.5 * Math.sin(entity.age * PITCH_RATE * TAU - 0.5 * Math.sin(entity.age * PITCH_RATE * TAU)));
                entity.setSnoutYaw(changeAngle(entity.getSnoutYaw(), targetYaw, 10));
                entity.setSnoutPitch(changeAngle(entity.getSnoutPitch(), targetPitch, 10));
            }
        }

        @Override
        protected boolean shouldStayHorizontal() {
            return false;
        }
    }
}
