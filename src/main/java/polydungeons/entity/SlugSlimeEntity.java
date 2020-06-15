package polydungeons.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class SlugSlimeEntity extends MobEntity {
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


    protected SlugSlimeEntity(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    public SlugSlimeEntity(World world) {
        super(PolyDungeonsEntities.SLUG_SLIME, world);
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
    public void baseTick() {
        super.baseTick();

        prevLeftEyeYaw = getLeftEyeYaw();
        prevLeftEyePitch = getLeftEyePitch();
        prevRightEyeYaw = getRightEyeYaw();
        prevRightEyePitch = getRightEyePitch();
        prevSnoutYaw = getSnoutYaw();
        prevSnoutPitch = getSnoutPitch();

        headYaw = 30;
        pitch = 20;

        if (!world.isClient) {
            setLeftEyeYaw(getLeftEyeYaw() + 3);
            setRightEyeYaw(getRightEyeYaw() - 3);
            setSnoutYaw(getSnoutYaw() + 1);
        }
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
}
