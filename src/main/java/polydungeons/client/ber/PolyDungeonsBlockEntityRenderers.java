package polydungeons.client.ber;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.resource.ResourceManager;
import polydungeons.block.entity.PolyDungeonsBlockEntities;
import polydungeons.client.ber.relic.RelicRenderParts;
import polydungeons.client.ber.relic.SamplerRelicRenderer;

import java.util.function.Consumer;
import java.util.function.Function;

@Environment(EnvType.CLIENT)
public class PolyDungeonsBlockEntityRenderers {
    private static <T extends BlockEntity> void register(BlockEntityType<T> type, Function<BlockEntityRenderDispatcher, BlockEntityRenderer<T>> constructor) {
        BlockEntityRendererRegistry.INSTANCE.register(type, constructor);
    }

    public static void onModelBakeEvent(ResourceManager manager, Consumer<ModelIdentifier> out) {
        out.accept(RelicRenderParts.RELIC_BASE);
        out.accept(RelicRenderParts.SAMPLER);
        out.accept(RelicRenderParts.LAVA);
    }

    public static void init() {
        ModelLoadingRegistry.INSTANCE.registerAppender(PolyDungeonsBlockEntityRenderers::onModelBakeEvent);
        register(PolyDungeonsBlockEntities.SAMPLER_RELIC, SamplerRelicRenderer::new);
    }
}
