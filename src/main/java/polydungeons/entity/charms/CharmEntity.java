package polydungeons.entity.charms;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import polydungeons.entity.IServerWorld;
import polydungeons.network.PolyDungeonsServerNetwork;

import java.util.Map;
import java.util.UUID;

public abstract class CharmEntity extends Entity {
    public CharmEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    public abstract ItemStack getItem();

    protected abstract Map<UUID, Vec3d> getPositionList(IServerWorld world);

    public Packet<?> createSpawnPacket() {
        return PolyDungeonsServerNetwork.spawnEntity(this);
    }

    @Override
    protected void initDataTracker() {
    }

    @Override
    protected void readCustomDataFromTag(CompoundTag tag) {
    }

    @Override
    protected void writeCustomDataToTag(CompoundTag tag) {
    }

    @Override
    public void updatePosition(double x, double y, double z) {
        super.updatePosition(x, y, z);
        if (!world.isClient) {
            getPositionList((IServerWorld) world).put(getUuid(), getPos());
        }
    }

    @Override
    public void kill() {
        super.kill();
        getPositionList((IServerWorld) world).remove(getUuid());
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (source.getSource() != null) {
            if(source.getSource() instanceof PlayerEntity) {
                World world = source.getSource().getEntityWorld();
                ItemEntity itemEntity = new ItemEntity(world, this.getX(), this.getY(), this.getZ(), getItem());
                world.spawnEntity(itemEntity);
                this.kill();
                return true;
            }
        }
        return false;
    }
}
