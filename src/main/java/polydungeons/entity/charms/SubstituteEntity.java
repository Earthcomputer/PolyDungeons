package polydungeons.entity.charms;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import polydungeons.entity.IServerWorld;
import polydungeons.entity.SlingshotProjectileEntity;
import polydungeons.item.PolyDungeonsItems;

import java.util.Map;
import java.util.UUID;

public class SubstituteEntity extends CharmEntity {
    public SubstituteEntity(EntityType<SubstituteEntity> type, World world) {
        super(type, world);
    }

    private static final TrackedData<Integer> CAPACITY = DataTracker.registerData(SlingshotProjectileEntity.class, TrackedDataHandlerRegistry.INTEGER);

    @Override
    public ItemStack getItem() {
        return new ItemStack(PolyDungeonsItems.SUBSTITUTE);
    }

    @Override
    protected Map<UUID, Vec3d> getPositionList(IServerWorld world) {
        return world.polydungeons_getSubstitutePositions();
    }

    public int getCapacity() {
        return dataTracker.get(CAPACITY);
    }

    public void setCapacity(int capacity) {
        dataTracker.set(CAPACITY, capacity);
    }

    @Override
    protected void initDataTracker() {
        dataTracker.startTracking(CAPACITY, 20);
    }

    @Override
    protected void readCustomDataFromTag(CompoundTag tag) {
        int capacity = tag.getInt("capacity");
        if(capacity != 0)
            setCapacity(capacity);
    }

    @Override
    protected void writeCustomDataToTag(CompoundTag tag) {
        tag.putInt("capacity", getCapacity());
    }
}
