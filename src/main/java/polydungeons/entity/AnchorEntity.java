package polydungeons.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import polydungeons.item.AnchorItem;
import polydungeons.item.PolyDungeonsItems;
import polydungeons.network.PolyDungeonsServerNetwork;

import java.util.ArrayList;
import java.util.List;

public class AnchorEntity extends FloatingItemEntity {
    public static final float RADIUS = 100;

    public AnchorEntity(EntityType<?> type, World world) {
        super(type, world);
        anchorsInWorld.add(this);
    }

    public static List<AnchorEntity> anchorsInWorld = new ArrayList<>();

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
    public boolean hasNoGravity() {
        return true;
    }

    public boolean collides() {
        return true;
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

    @Override
    public void kill() {
        anchorsInWorld.remove(this);
        super.kill();
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(PolyDungeonsItems.ANCHOR);
    }
}
