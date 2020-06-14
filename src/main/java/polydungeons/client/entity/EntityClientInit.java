package polydungeons.client.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.entity.EntityType;
import polydungeons.client.entity.renderer.BlazingMechanicalEntityRenderer;
import polydungeons.client.entity.renderer.FloatingItemEntityRenderer;
import polydungeons.client.entity.renderer.SpearEntityRenderer;
import polydungeons.client.entity.renderer.SplatEntityRenderer;
import polydungeons.entity.PolyDungeonsEntities;

import java.util.function.Function;

@Environment(EnvType.CLIENT)
public class EntityClientInit {
    public static void registerAll() {
        register(PolyDungeonsEntities.BLAZING_MECHANICAL, BlazingMechanicalEntityRenderer::new);
        register(PolyDungeonsEntities.ANCHOR, FloatingItemEntityRenderer::new);
        EntityRendererRegistry.INSTANCE.register(PolyDungeonsEntities.SLINGSHOT_PROJECTILE, (dispatcher, context) -> new FlyingItemEntityRenderer<>(dispatcher, context.getItemRenderer()));
        register(PolyDungeonsEntities.SPEAR, SpearEntityRenderer::new);
        register(PolyDungeonsEntities.SPLAT, SplatEntityRenderer::new);
    }

    private static void register(EntityType<?> entityType, Function<EntityRenderDispatcher, EntityRenderer<?>> rendererCreator) {
        EntityRendererRegistry.INSTANCE.register(entityType, (dispatcher, context) -> rendererCreator.apply(dispatcher));
    }
}
