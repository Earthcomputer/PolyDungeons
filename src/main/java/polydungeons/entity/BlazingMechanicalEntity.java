package polydungeons.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntityWithAi;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BlazingMechanicalEntity extends MobEntityWithAi {
    protected BlazingMechanicalEntity(EntityType<? extends MobEntityWithAi> entityType, World world) {
        super(entityType, world);
    }
}
