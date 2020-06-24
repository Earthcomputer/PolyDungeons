package polydungeons.client.ber;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.EndPortalBlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import polydungeons.block.entity.DecorativeEndBlockEntity;

public class SolidEndPortalBlockEntityRenderer extends EndPortalBlockEntityRenderer<DecorativeEndBlockEntity> {
    public SolidEndPortalBlockEntityRenderer(BlockEntityRenderDispatcher blockEntityRenderDispatcher) {
        super(blockEntityRenderDispatcher);
    }

    @Override
    protected int method_3592(double d) {
        return super.method_3592(d) + 1;
    }

    @Override
    protected float method_3594() {
        return 1.0F;
    }
}
