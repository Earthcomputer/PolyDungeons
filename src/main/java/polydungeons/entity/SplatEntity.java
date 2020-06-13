package polydungeons.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.fabricmc.fabric.api.server.PlayerStream;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import polydungeons.network.PolyDungeonsServerNetwork;

public class SplatEntity extends Entity {
    private static final TrackedData<Boolean> FIERY = DataTracker.registerData(SplatEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Direction> DIRECTION = DataTracker.registerData(SplatEntity.class, TrackedDataHandlerRegistry.FACING);

    private boolean hasInitialized;
    private int lastSyncedRemainingTicks;
    private int remainingTicks;

    public SplatEntity(EntityType<? extends SplatEntity> entityType, World world) {
        super(entityType, world);
        hasInitialized = true;
        updateBoundingBox();
    }

    public SplatEntity(World world) {
        super(PolyDungeonsEntities.SPLAT, world);
        hasInitialized = true;
        updateBoundingBox();
    }

    @Override
    protected void initDataTracker() {
        dataTracker.startTracking(FIERY, false);
        dataTracker.startTracking(DIRECTION, Direction.UP);
    }

    public boolean isFiery() {
        return dataTracker.get(FIERY);
    }

    public void setFiery(boolean fiery) {
        dataTracker.set(FIERY, fiery);
    }

    public int getRemainingTicks() {
        return remainingTicks;
    }

    public void setRemainingTicks(int remainingTicks) {
        this.remainingTicks = remainingTicks;
    }

    public Direction getDirection() {
        return dataTracker.get(DIRECTION);
    }

    public void setDirection(Direction direction) {
        dataTracker.set(DIRECTION, direction);
    }

    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        super.onTrackedDataSet(data);
        if (data == DIRECTION) {
            updateBoundingBox();
        }
    }

    @Override
    public void updatePosition(double x, double y, double z) {
        setPos(x, y, z);
        updateBoundingBox();
    }

    private void updateBoundingBox() {
        if (!hasInitialized) {
            return;
        }
        Direction direction = getDirection();
        float shrinkScale = getShrinkScale(0);
        double bbX = direction.getAxis() == Direction.Axis.X ? 0.0625 : 0.5 * shrinkScale;
        double bbY = direction.getAxis() == Direction.Axis.Y ? 0.0625 : 0.5 * shrinkScale;
        double bbZ = direction.getAxis() == Direction.Axis.Z ? 0.0625 : 0.5 * shrinkScale;
        Vec3d pos = getPos();
        setBoundingBox(new Box(pos.x - bbX, pos.y - bbY, pos.z - bbZ, pos.x + bbX, pos.y + bbY, pos.z + bbZ));
    }

    public float getShrinkScale(float tickDelta) {
        return MathHelper.clamp((remainingTicks - tickDelta) * 0.01f, 0.01f, 1f);
    }

    @Override
    protected void readCustomDataFromTag(CompoundTag tag) {
        setFiery(tag.getBoolean("Fiery"));
        remainingTicks = tag.getInt("RemainingTicks");
        setDirection(Direction.byId(tag.getByte("Direction")));
    }

    @Override
    protected void writeCustomDataToTag(CompoundTag tag) {
        tag.putBoolean("Fiery", isFiery());
        tag.putInt("RemainingTicks", remainingTicks);
        tag.putByte("Direction", (byte)getDirection().getId());
    }

    @Override
    public void tick() {
        super.tick();

        if (!world.isClient && lastSyncedRemainingTicks != remainingTicks) {
            Packet<?> packet = PolyDungeonsServerNetwork.splatRemainingTicks(this, remainingTicks);
            PlayerStream.watching(this).forEach(player -> ServerSidePacketRegistry.INSTANCE.sendToPlayer(player, packet));
        }
        remainingTicks--;
        lastSyncedRemainingTicks = remainingTicks;

        updateBoundingBox();

        float shrinkScale = getShrinkScale(0);
        if (world.isClient && isFiery() && random.nextFloat() * 5 < shrinkScale * shrinkScale) {
            world.addParticle(
                    ParticleTypes.FLAME,
                    MathHelper.nextDouble(random, getBoundingBox().minX, getBoundingBox().maxX),
                    MathHelper.nextDouble(random, getBoundingBox().minY, getBoundingBox().maxY),
                    MathHelper.nextDouble(random, getBoundingBox().minZ, getBoundingBox().maxZ),
                    0, 0, 0
            );
        }

        if (remainingTicks <= 0) {
            remove();
        }
    }

    @Environment(EnvType.CLIENT)
    public boolean shouldRender(double distance) {
        double maxDistance = getBoundingBox().getAverageSideLength() * 10;
        if (Double.isNaN(maxDistance)) {
            maxDistance = 1;
        }
        maxDistance *= 64 * getRenderDistanceMultiplier();
        return distance < maxDistance * maxDistance;
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return PolyDungeonsServerNetwork.spawnEntity(this, buf -> buf.writeVarInt(remainingTicks));
    }
}
