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
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import polydungeons.network.PolyDungeonsServerNetwork;
import polydungeons.sound.PolyDungeonsSoundEvents;

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
    protected SoundEvent getHitSound() {
        return PolyDungeonsSoundEvents.SPLAT;
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

    @Override
    protected void onHit(LivingEntity target) {
        super.onHit(target);
        if (isFiery()) {
            target.setOnFireFor(2);
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        if (!world.isClient) {
            SplatEntity splat = new SplatEntity(world);
            BlockPos splatBlockPos = blockHitResult.getBlockPos().offset(blockHitResult.getSide());
            double splatX = blockHitResult.getSide().getAxis() == Direction.Axis.X ? splatBlockPos.getX() + 0.5 - 0.4375 * blockHitResult.getSide().getOffsetX() : getX();
            double splatY = blockHitResult.getSide().getAxis() == Direction.Axis.Y ? splatBlockPos.getY() + 0.5 - 0.4375 * blockHitResult.getSide().getOffsetY() : getY();
            double splatZ = blockHitResult.getSide().getAxis() == Direction.Axis.Z ? splatBlockPos.getZ() + 0.5 - 0.4375 * blockHitResult.getSide().getOffsetZ() : getZ();
            splat.updatePosition(splatX, splatY, splatZ);
            splat.setDirection(blockHitResult.getSide());
            splat.setFiery(isFiery());
            splat.setRemainingTicks(300);
            world.spawnEntity(splat);
        }

        remove();
    }
}
