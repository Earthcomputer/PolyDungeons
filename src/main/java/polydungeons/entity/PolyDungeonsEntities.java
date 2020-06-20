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
import polydungeons.entity.charms.AnchorEntity;

public class PolyDungeonsEntities {
    public static final EntityType<BiglinEntity> BIGLIN = register(
            "biglin",
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, BiglinEntity::new)
            .dimensions(EntityDimensions.fixed(0.75f, 2f)).build()
    );

    public static final EntityType<BlazingMechanicalEntity> BLAZING_MECHANICAL = register(
            "blazing_mechanical",
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, BlazingMechanicalEntity::new)
            .dimensions(EntityDimensions.fixed(0.75f, 2f)).build()
    );
    public static final EntityType<SlingshotProjectileEntity> SLINGSHOT_PROJECTILE = register(
            "slingshot_projectile",
            FabricEntityTypeBuilder.<SlingshotProjectileEntity>create(SpawnGroup.MISC, SlingshotProjectileEntity::new)
            .dimensions(EntityDimensions.fixed(0.25f, 0.25f)).trackable(145, 3).build()
    );

    // Charms
    public static final EntityType<AnchorEntity> ANCHOR = register(
            "anchor",
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, AnchorEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25f, 0.25f)).build()
    );

    public static final EntityType<SpearEntity> SPEAR = register(
            "spear",
            FabricEntityTypeBuilder.<SpearEntity>create(SpawnGroup.MISC, SpearEntity::new)
                    .dimensions(EntityDimensions.fixed(1f, 1f)).build()
    );
    public static final EntityType<SplatEntity> SPLAT = register(
            "splat",
            FabricEntityTypeBuilder.<SplatEntity>create(SpawnGroup.MISC, SplatEntity::new)
            .dimensions(EntityDimensions.fixed(0.5f, 0.5f)).trackable(145, 3).build()
    );
    public static final EntityType<SlugSlimeEntity> SLUG_SLIME = register(
            "slug_slime",
            FabricEntityTypeBuilder.<SlugSlimeEntity>create(SpawnGroup.MONSTER, SlugSlimeEntity::new)
            .dimensions(EntityDimensions.fixed(1f, 2f)).build()
    );

    private static <T extends Entity> EntityType<T> register(String id, EntityType<T> entityType) {
        return Registry.register(Registry.ENTITY_TYPE, new Identifier(PolyDungeons.MODID, id), entityType);
    }

    public static void registerAll() {
        FabricDefaultAttributeRegistry.register(BLAZING_MECHANICAL, BlazingMechanicalEntity.createBlazeAttributes());
        FabricDefaultAttributeRegistry.register(SLUG_SLIME, SlugSlimeEntity.createSlugSlimeAttributes());

        FabricDefaultAttributeRegistry.register(BIGLIN, BiglinEntity.createBiglinAttributes());
    }
}
