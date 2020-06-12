package polydungeons.client.entity.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.Entity;
import polydungeons.client.entity.EntityUtils;
import polydungeons.entity.BlazingMechanicalEntity;

public class BlazingMechanicalModel extends EntityModel<BlazingMechanicalEntity> {
    private final ModelPart shields;
    private final ModelPart rods;
    private final ModelPart base;
    private final ModelPart helmet;
    private final ModelPart frontplate;
    private final ModelPart spikes;

    public BlazingMechanicalModel() {
        textureWidth = 60;
        textureHeight = 100;

        shields = new ModelPart(this, 0, 0);
        shields.setPivot(0.0F, 22.0F, 0.0F);
        EntityUtils.setRotationAngle(shields, 0.0F, 0.7854F, 0.0F);
        shields.setTextureOffset(28, 0).addCuboid(-6.0F, -24.0F, -12.0F, 12.0F, 24.0F, 2.0F, 0.0F, false);
        shields.setTextureOffset(28, 0).addCuboid(-6.0F, -24.0F, 12.0F, 12.0F, 24.0F, 2.0F, 0.0F, false);
        shields.setTextureOffset(0, 0).addCuboid(-14.0F, -24.0F, -6.0F, 2.0F, 24.0F, 12.0F, 0.0F, false);
        shields.setTextureOffset(0, 0).addCuboid(10.0F, -24.0F, -6.0F, 2.0F, 24.0F, 12.0F, 0.0F, false);

        rods = new ModelPart(this, 0, 0);
        rods.setPivot(0.0F, 24.0F, 0.0F);
        rods.setTextureOffset(30, 80).addCuboid(-1.0F, -22.0F, -8.0F, 2.0F, 18.0F, 2.0F, 0.0F, false);
        rods.setTextureOffset(30, 80).addCuboid(-1.0F, -22.0F, 6.0F, 2.0F, 18.0F, 2.0F, 0.0F, true);
        rods.setTextureOffset(30, 80).addCuboid(-8.0F, -22.0F, -1.0F, 2.0F, 18.0F, 2.0F, 0.0F, true);
        rods.setTextureOffset(30, 80).addCuboid(6.0F, -22.0F, -1.0F, 2.0F, 18.0F, 2.0F, 0.0F, false);

        base = new ModelPart(this, 0, 0);
        base.setPivot(0.0F, 24.0F, 0.0F);
        base.setTextureOffset(23, 59).addCuboid(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
        base.setTextureOffset(28, 27).addCuboid(-4.0F, -32.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
        base.setTextureOffset(34, 48).addCuboid(-2.0F, -24.0F, -2.0F, 4.0F, 22.0F, 4.0F, 0.0F, false);

        helmet = new ModelPart(this, 0, 0);
        helmet.setPivot(0.0F, -25.0F, 0.0F);
        base.addChild(helmet);
        helmet.setTextureOffset(0, 82).addCuboid(-5.0F, -7.0F, -5.0F, 1.0F, 9.0F, 9.0F, 0.0F, false);
        helmet.setTextureOffset(0, 82).addCuboid(4.0F, -7.0F, -5.0F, 1.0F, 9.0F, 9.0F, 0.0F, false);
        helmet.setTextureOffset(0, 72).addCuboid(-5.0F, -7.0F, 4.0F, 10.0F, 9.0F, 1.0F, 0.0F, false);

        frontplate = new ModelPart(this, 0, 0);
        frontplate.setPivot(0.0F, 0.0F, 0.0F);
        helmet.addChild(frontplate);
        frontplate.setTextureOffset(0, 65).addCuboid(-4.0F, -3.0F, -5.0F, 1.0F, 5.0F, 1.0F, 0.0F, false);
        frontplate.setTextureOffset(0, 65).addCuboid(3.0F, -3.0F, -5.0F, 1.0F, 5.0F, 1.0F, 0.0F, false);
        frontplate.setTextureOffset(4, 68).addCuboid(-4.0F, -7.0F, -5.0F, 8.0F, 2.0F, 1.0F, 0.0F, false);

        spikes = new ModelPart(this, 0, 0);
        spikes.setPivot(0.0F, 0.0F, 0.0F);
        helmet.addChild(spikes);
        spikes.setTextureOffset(4, 65).addCuboid(-3.0F, -8.0F, -5.0F, 6.0F, 1.0F, 1.0F, 0.0F, false);
        spikes.setTextureOffset(19, 65).addCuboid(-2.0F, -9.0F, -5.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
        spikes.setTextureOffset(0, 52).addCuboid(4.0F, -8.0F, -5.0F, 1.0F, 1.0F, 10.0F, 0.0F, false);
        spikes.setTextureOffset(0, 52).addCuboid(-5.0F, -8.0F, -5.0F, 1.0F, 1.0F, 10.0F, 0.0F, false);
        spikes.setTextureOffset(0, 49).addCuboid(-4.0F, -8.0F, 4.0F, 8.0F, 1.0F, 1.0F, 0.0F, false);
    }

    @Override
    public void setAngles(BlazingMechanicalEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        shields.yaw = (float)Math.toRadians(180) * animationProgress * 0.05f;
        //shields.pivotY = 22.0F + (float)Math.cos(animationProgress * 0);
        rods.yaw = (float)Math.toRadians(-180) * animationProgress * 0.06f;
        rods.pivotY = 24.0F + (float)Math.cos(animationProgress * 0.1);
        base.pivotY = 24.0F + (float)Math.cos(animationProgress * 0.1);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumer vertices, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        shields.render(matrixStack, vertices, packedLight, packedOverlay);
        rods.render(matrixStack, vertices, packedLight, packedOverlay);
        base.render(matrixStack, vertices, packedLight, packedOverlay);
    }
}
