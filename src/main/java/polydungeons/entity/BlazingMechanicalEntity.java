package polydungeons.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.world.World;

public class BlazingMechanicalEntity extends BlazeEntity {
    protected BlazingMechanicalEntity(EntityType<? extends BlazeEntity> entityType, World world) {
        super(entityType, world);
    }
}
