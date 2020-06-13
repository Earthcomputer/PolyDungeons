package polydungeons.entity;

import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.fabricmc.fabric.api.server.PlayerStream;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import polydungeons.network.PolyDungeonsServerNetwork;

public class SplatEntity extends Entity {
    private static final TrackedData<Boolean> FIERY = DataTracker.registerData(SplatEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Direction> DIRECTION = DataTracker.registerData(SplatEntity.class, TrackedDataHandlerRegistry.FACING);

    private int lastSyncedRemainingTicks;
    private int remainingTicks;

    public SplatEntity(EntityType<? extends SplatEntity> entityType, World world) {
        super(entityType, world);
        setDirection(Direction.UP);
    }

    public SplatEntity(World world) {
        super(PolyDungeonsEntities.SPLAT, world);
        setDirection(Direction.UP);
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

        // update bounding box
        double bbX = direction.getAxis() == Direction.Axis.X ? 0.125 : 1;
        double bbY = direction.getAxis() == Direction.Axis.Y ? 0.125 : 1;
        double bbZ = direction.getAxis() == Direction.Axis.Z ? 0.125 : 1;
        Vec3d pos = getPos();
        setBoundingBox(new Box(pos.x - bbX, pos.y - bbY, pos.z - bbZ, pos.x + bbX, pos.y + bbY, pos.z + bbZ));
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
        if (remainingTicks <= 0) {
            remove();
        }
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return PolyDungeonsServerNetwork.spawnEntity(this, buf -> buf.writeVarInt(remainingTicks));
    }
}
