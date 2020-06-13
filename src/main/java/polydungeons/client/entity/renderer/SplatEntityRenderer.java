package polydungeons.client.entity.renderer;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.client.util.math.Vector4f;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Quaternion;
import polydungeons.PolyDungeons;
import polydungeons.entity.SplatEntity;

public class SplatEntityRenderer extends EntityRenderer<SplatEntity> {
    private static final Identifier SLIME_TEX = new Identifier(PolyDungeons.MODID, "textures/entity/splat_slime.png");
    private static final Identifier MAGMA_TEX = new Identifier(PolyDungeons.MODID, "textures/entity/splat_magma.png");

    public SplatEntityRenderer(EntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(SplatEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        float remainingTicks = entity.getRemainingTicks() - tickDelta;
        matrices.push();

        // squish and scale
        float overallScale = MathHelper.clamp(remainingTicks * 0.01f, 0.01f, 1f);
        float squishAngle = remainingTicks * 0.01f * (float) Math.PI;
        float squishMagnitude = MathHelper.cos(remainingTicks * 0.05f * (float)Math.PI);
        float squishX = 0.9f + 0.2f * squishMagnitude * MathHelper.cos(squishAngle);
        float squishZ = 0.9f + 0.2f * squishMagnitude * MathHelper.sin(squishAngle);
        matrices.scale(overallScale * squishX, 1, overallScale * squishZ);

        // apply direction
        switch (entity.getDirection()) {
            case UP:
            default:
                // already good
                break;
            case DOWN:
                matrices.multiply(new Quaternion(Vector3f.NEGATIVE_X, 180, true));
                break;
            case WEST:
                matrices.multiply(new Quaternion(Vector3f.NEGATIVE_Z, 90, true));
                break;
            case EAST:
                matrices.multiply(new Quaternion(Vector3f.POSITIVE_Z, 90, true));
                break;
            case NORTH:
                matrices.multiply(new Quaternion(Vector3f.POSITIVE_X, 90, true));
                break;
            case SOUTH:
                matrices.multiply(new Quaternion(Vector3f.NEGATIVE_X, 90, true));
        }

        // actually render it
        int overlay = OverlayTexture.getUv(0, false);
        VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(getTexture(entity)));
        Matrix4f modelMatrix = matrices.peek().getModel();
        Vector4f v00 = new Vector4f((float)entity.getX() - 0.5f, (float)entity.getY(), (float)entity.getZ() - 0.5f, 1f);
        v00.transform(modelMatrix);
        Vector4f v01 = new Vector4f((float)entity.getX() - 0.5f, (float)entity.getY(), (float)entity.getZ() + 0.5f, 1f);
        v01.transform(modelMatrix);
        Vector4f v10 = new Vector4f((float)entity.getX() + 0.5f, (float)entity.getY(), (float)entity.getZ() + 0.5f, 1f);
        v10.transform(modelMatrix);
        Vector4f v11 = new Vector4f((float)entity.getX() + 0.5f, (float)entity.getY(), (float)entity.getZ() - 0.5f, 1f);
        v11.transform(modelMatrix);
        buffer.vertex(v00.getX(), v00.getY(), v00.getZ(), 1f, 1f, 1f, 1f, 0, 0, overlay, light, 0, 1, 0);
        buffer.vertex(v01.getX(), v01.getY(), v01.getZ(), 1f, 1f, 1f, 1f, 0, 1, overlay, light, 0, 1, 0);
        buffer.vertex(v10.getX(), v10.getY(), v10.getZ(), 1f, 1f, 1f, 1f, 1, 1, overlay, light, 0, 1, 0);
        buffer.vertex(v11.getX(), v11.getY(), v11.getZ(), 1f, 1f, 1f, 1f, 1, 0, overlay, light, 0, 1, 0);

        matrices.pop();

        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(SplatEntity entity) {
        return entity.isFiery() ? MAGMA_TEX : SLIME_TEX;
    }
}
