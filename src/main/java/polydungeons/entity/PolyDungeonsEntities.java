package polydungeons.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import polydungeons.PolyDungeons;

public class PolyDungeonsEntities {
    public static final EntityType<BlazingMechanicalEntity> BLAZING_MECHANICAL = register(
            "blazing_mechanical",
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, BlazingMechanicalEntity::new)
            .dimensions(EntityDimensions.fixed(0.75f, 2f)).build()
    );
    public static final EntityType<SlingshotProjectileEntity> SLINGSHOT_PROJECTILE = register(
            "slingshot_projectile",
            FabricEntityTypeBuilder.<SlingshotProjectileEntity>create(SpawnGroup.MISC, SlingshotProjectileEntity::new)
            .dimensions(EntityDimensions.fixed(0.25f, 0.25f)).build()
    );

    private static <T extends Entity> EntityType<T> register(String id, EntityType<T> entityType) {
        return Registry.register(Registry.ENTITY_TYPE, new Identifier(PolyDungeons.MODID, id), entityType);
    }

    public static void registerAll() {
        FabricDefaultAttributeRegistry.register(BLAZING_MECHANICAL, BlazingMechanicalEntity.createBlazeAttributes());
    }
}
