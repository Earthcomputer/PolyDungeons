package polydungeons.client.ber;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;

@Environment(EnvType.CLIENT)
public class BlockRenderUtils {
    public static void drawBlockModel(MatrixStack matrices, VertexConsumerProvider vcp, int light, ModelIdentifier id) {
        MinecraftClient.getInstance().getBlockRenderManager().getModelRenderer().render(
                matrices.peek(),
                vcp.getBuffer(RenderLayer.getSolid()),
                null,
                MinecraftClient.getInstance().getBakedModelManager().getModel(id),
                1f, 1f, 1f,
                Math.max(light - 2, 0),
                OverlayTexture.DEFAULT_UV
        );
    }
}
