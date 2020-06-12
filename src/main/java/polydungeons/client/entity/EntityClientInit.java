package polydungeons.client.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.entity.EntityType;
import polydungeons.entity.PolyDungeonsEntities;

import java.util.function.Function;

@Environment(EnvType.CLIENT)
public class EntityClientInit {
    public static void registerAll() {
        register(PolyDungeonsEntities.BLAZING_MECHANICAL, BlazingMechanicalRenderer::new);
        EntityRendererRegistry.INSTANCE.register(PolyDungeonsEntities.SLINGSHOT_PROJECTILE, (dispatcher, context) -> new FlyingItemEntityRenderer<>(dispatcher, context.getItemRenderer()));
    }

    private static void register(EntityType<?> entityType, Function<EntityRenderDispatcher, EntityRenderer<?>> rendererCreator) {
        EntityRendererRegistry.INSTANCE.register(entityType, (dispatcher, context) -> rendererCreator.apply(dispatcher));
    }
}
