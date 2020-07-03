package polydungeons.client.ber;

import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.EndPortalBlockEntityRenderer;
import polydungeons.block.entity.DecorativeEndBlockEntity;

public class SolidEndPortalBlockEntityRenderer extends EndPortalBlockEntityRenderer<DecorativeEndBlockEntity> {
    public SolidEndPortalBlockEntityRenderer(BlockEntityRenderDispatcher blockEntityRenderDispatcher) {
        super(blockEntityRenderDispatcher);
    }

    @Override
    protected int method_3592(double d) {
        // Decrease the distance at which the higher layers stop being rendered
        return super.method_3592(d * 3.0D) + 1;
    }

    @Override
    protected float method_3594() {
        return 1.0F;
    }
}
