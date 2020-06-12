package polydungeons.client.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import polydungeons.entity.PolyDungeonsEntities;

@Environment(EnvType.CLIENT)
public class EntityClientInit {
    public static void registerAll() {
        EntityRendererRegistry.INSTANCE.register(PolyDungeonsEntities.TEST_ENTITY, (dispatcher, context) -> {
            return new BlazingMechanicalRenderer(dispatcher);
        });
    }
}
