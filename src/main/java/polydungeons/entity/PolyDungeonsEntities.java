package polydungeons.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import polydungeons.PolyDungeons;

public class PolyDungeonsEntities {
    public static final EntityType<BlazingMechanicalEntity> BLAZING_MECHANICAL = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(PolyDungeons.MODID, "blazing_mechanical"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, BlazingMechanicalEntity::new)
            .dimensions(EntityDimensions.fixed(0.75f, 2f)).build()
    );

    public static void registerAll() {
        FabricDefaultAttributeRegistry.register(BLAZING_MECHANICAL, BlazingMechanicalEntity.createBlazeAttributes());
    }
}
