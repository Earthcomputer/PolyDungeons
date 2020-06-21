package polydungeons.client.ber.relic;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import polydungeons.block.entity.relic.SamplerRelicBlockEntity;
import polydungeons.client.ber.BlockRenderUtils;

public class SamplerRelicRenderer extends BlockEntityRenderer<SamplerRelicBlockEntity> {
    public SamplerRelicRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    public static final int ANIMATION_FRAMES = 300;

    int animationFrame = 0;

    @Override
    public void render(SamplerRelicBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vcp, int light, int overlay) {
        matrices.push();
        BlockRenderUtils.drawBlockModel(matrices, vcp, light, RelicRenderParts.RELIC_BASE);
        matrices.push();
        matrices.translate(1/16d, 4/16d, 1/16d);
        matrices.scale(13/16f, 2/16f, 13/16f);
        BlockRenderUtils.drawBlockModel(matrices, vcp, light, RelicRenderParts.LAVA);
        matrices.pop();
        matrices.translate(0.5, 0.5, 0.5);
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(360f * ((float)animationFrame / ANIMATION_FRAMES)));
        matrices.translate(-0.5, -0.5, -0.5);
        BlockRenderUtils.drawBlockModel(matrices, vcp, light, RelicRenderParts.SAMPLER);
        matrices.pop();
        animationFrame++;
        if(animationFrame >= ANIMATION_FRAMES) animationFrame = 0;
    }
}
