package polydungeons.entity.charms;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.World;
import polydungeons.entity.FloatingItemEntity;
import polydungeons.item.PolyDungeonsItems;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AnchorEntity extends FloatingItemEntity {
    public static final float RADIUS = 100;

    public static List<UUID> allAnchors = new ArrayList<>();

    public AnchorEntity(EntityType<?> type, World world) {
        super(type, world);
        allAnchors.add(getUuid());
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
    public boolean hasNoGravity() {
        return true;
    }

    public boolean collides() {
        return true;
    }

    @Override
    public void kill() {
        allAnchors.remove(getUuid());
        super.kill();
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(PolyDungeonsItems.ANCHOR);
    }
}
