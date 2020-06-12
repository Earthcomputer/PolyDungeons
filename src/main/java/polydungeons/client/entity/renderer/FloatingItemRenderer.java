package polydungeons.client.entity.renderer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import polydungeons.entity.FloatingItemEntity;

public class FloatingItemRenderer extends EntityRenderer<FloatingItemEntity> {

    public FloatingItemRenderer(EntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public Identifier getTexture(FloatingItemEntity entity) {
        return null;
    }

    @Override
    protected int getBlockLight(FloatingItemEntity entity, BlockPos blockPos) {
        return 15;
    }

    @Override
    public void render(FloatingItemEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        matrices.translate(0, 0, 0);
        matrices.translate(0, Math.cos(entity.age * 0.05) * 0.1, 0);
        matrices.multiply(Vector3f.NEGATIVE_Y.getRadialQuaternion(entity.age * 0.01f));
        ItemStack itemStack = entity.getItem();
        MinecraftClient.getInstance().getItemRenderer()
                .renderItem(itemStack, ModelTransformation.Mode.GROUND, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers);
        matrices.pop();

        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }
}
