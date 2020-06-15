package polydungeons.client.entity.renderer;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import polydungeons.PolyDungeons;
import polydungeons.client.entity.model.SpearModel;
import polydungeons.entity.SpearEntity;

public class SpearEntityRenderer extends EntityRenderer<SpearEntity> {
	public static final Identifier TEXTURE = new Identifier(PolyDungeons.MODID, "textures/entity/spear.png");
	private final SpearModel model = new SpearModel();

	public SpearEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
		super(entityRenderDispatcher);
	}

	public void render(SpearEntity spearEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
		matrixStack.push();
		matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(MathHelper.lerp(g, spearEntity.prevYaw, spearEntity.yaw) - 90.0F));
		matrixStack.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(MathHelper.lerp(g, spearEntity.prevPitch, spearEntity.pitch) + 90.0F));
		VertexConsumer vertexConsumer = ItemRenderer.method_29711(vertexConsumerProvider, this.model.getLayer(this.getTexture(spearEntity)), false, false);
		this.model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
		matrixStack.pop();
		super.render(spearEntity, f, g, matrixStack, vertexConsumerProvider, i);
	}

	public Identifier getTexture(SpearEntity spearEntity) {
		return TEXTURE;
	}
}
