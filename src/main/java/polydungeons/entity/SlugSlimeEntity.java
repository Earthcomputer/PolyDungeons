package polydungeons.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;

public class SlugSlimeEntity extends MobEntity {
    protected SlugSlimeEntity(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    public SlugSlimeEntity(World world) {
        super(PolyDungeonsEntities.SLUG_SLIME, world);
    }
}
