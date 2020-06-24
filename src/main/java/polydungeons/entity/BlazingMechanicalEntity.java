package polydungeons.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.World;

public class BlazingMechanicalEntity extends BlazeEntity {
    protected BlazingMechanicalEntity(EntityType<? extends BlazeEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createBlazeAttributes() {
        DefaultAttributeContainer.Builder builder = BlazeEntity.createBlazeAttributes();
        builder.add(EntityAttributes.GENERIC_ARMOR, 4.0d);
        builder.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.5d);
        return builder;
    }
}
