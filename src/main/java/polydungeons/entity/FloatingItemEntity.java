package polydungeons.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
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
