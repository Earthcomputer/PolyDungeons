package polydungeons.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.world.World;
import polydungeons.network.PolyDungeonsServerNetwork;

public abstract class FloatingItemEntity extends Entity {
    public FloatingItemEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    public abstract ItemStack getItem();

    public Packet<?> createSpawnPacket() {
        return PolyDungeonsServerNetwork.spawnEntity(this);
    }
}
