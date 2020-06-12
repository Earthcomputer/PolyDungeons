package polydungeons.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvironmentInterface;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.world.World;
import polydungeons.network.PolyDungeonsServerNetwork;

@EnvironmentInterface(value = EnvType.CLIENT, itf = FlyingItemEntity.class)
public class SlingshotProjectileEntity extends PersistentProjectileEntity implements FlyingItemEntity {
    private static final TrackedData<Boolean> FIERY = DataTracker.registerData(SlingshotProjectileEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public SlingshotProjectileEntity(EntityType<? extends SlingshotProjectileEntity> type, World world) {
        super(type, world);
    }

    public SlingshotProjectileEntity(LivingEntity owner, World world) {
        super(PolyDungeonsEntities.SLINGSHOT_PROJECTILE, owner, world);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        dataTracker.startTracking(FIERY, false);
    }

    public boolean isFiery() {
        return dataTracker.get(FIERY);
    }

    public void setFiery(boolean fiery) {
        dataTracker.set(FIERY, fiery);
    }

    @Override
    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
        tag.putBoolean("Fiery", isFiery());
    }

    @Override
    public void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
        setFiery(tag.getBoolean("Fiery"));
    }

    @Override
    public ItemStack asItemStack() {
        return ItemStack.EMPTY;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public ItemStack getStack() {
        return isFiery() ? new ItemStack(Items.MAGMA_CREAM) : new ItemStack(Items.SLIME_BALL);
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return PolyDungeonsServerNetwork.spawnEntity(this, buf -> {
            Entity owner = getOwner();
            buf.writeVarInt(owner != null ? owner.getEntityId() : 0);
        });
    }
}
