package polydungeons.client.entity.model;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class SpearModel extends Model {
	public static final Identifier TEXTURE = new Identifier("textures/entity/trident.png");
	private final ModelPart shaft = new ModelPart(this, 0, 0);
	private final ModelPart tip1 = new ModelPart(this, 0, 0);
	private final ModelPart tip2 = new ModelPart(this, 0, 0);

	public SpearModel() {
		super(RenderLayer::getEntityCutout);
		this.shaft.setTextureOffset(0,5).addCuboid(-0.5f, 2.0f, -0.5f, 1.0f, 25.0f, 1.0f, 0.0f);

		this.tip1.addCuboid(-0.5f-2.5f, 2.0f, -0.5f-2.5f, 5.0f, 5.0f, 5.0f, 0.0f);
	}

	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		this.shaft.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		this.tip1.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		this.tip2.render(matrices, vertices, light, overlay, red, green, blue, alpha);
	}
}
