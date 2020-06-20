package polydungeons.entity.charms;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.World;
import polydungeons.entity.FloatingItemEntity;
import polydungeons.entity.SlingshotProjectileEntity;
import polydungeons.item.PolyDungeonsItems;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SubstituteEntity extends FloatingItemEntity {
    public SubstituteEntity(EntityType<SubstituteEntity> type, World world) {
        super(type, world);
        allSubstitutes.add(this.getUuid());
    }

    public static List<UUID> allSubstitutes = new ArrayList<>();

    private static final TrackedData<Integer> CAPACITY = DataTracker.registerData(SlingshotProjectileEntity.class, TrackedDataHandlerRegistry.INTEGER);

    @Override
    public ItemStack getItem() {
        return new ItemStack(PolyDungeonsItems.SUBSTITUTE);
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
    public void kill() {
        allSubstitutes.remove(this.getUuid());
        super.kill();
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
