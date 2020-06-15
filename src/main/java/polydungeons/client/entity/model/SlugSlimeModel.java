package polydungeons.client.entity.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.util.math.Direction;
import polydungeons.entity.SlugSlimeEntity;

import java.util.ArrayList;
import java.util.List;

public class SlugSlimeModel extends CompositeEntityModel<SlugSlimeEntity> {
    private final ModelPart body;
    private final ModelPart bodyNoTop;
    private final ModelPart head;
    private final ModelPart leftEyePole;
    private final ModelPart leftEyePoleNoBottom;
    private final ModelPart rightEyePole;
    private final ModelPart rightEyePoleNoBottom;
    private final ModelPart snout;
    private final List<ModelPart> children = new ArrayList<>();

    public SlugSlimeModel() {
        super(RenderLayer::getEntityTranslucent);

        textureWidth = 128;
        textureHeight = 128;

        body = createBody(true);
        bodyNoTop = createBody(false);

        head = new ModelPart(this);
        head.setPivot(0.0F, 2.0F, 0.0F);
        head.setTextureOffset(34, 34).addCuboid(-3.0F, -12.0F, -3.0F, 6.0F, 12.0F, 6.0F, 0.0F, false);

        leftEyePole = createLeftEyePole(true);
        leftEyePoleNoBottom = createLeftEyePole(false);

        rightEyePole = createRightEyePole(true);
        rightEyePoleNoBottom = createRightEyePole(false);

        snout = new ModelPart(this);
        snout.setPivot(-3.0F, -6.0F, 0.0F);
        snout.setTextureOffset(0, 0).addCuboid(-2.0F, -1.0F, -1.0F, 2.0F, 7.0F, 2.0F, 0.0F, false);
        snout.setTextureOffset(0, 16).addCuboid(-3.0F, 6.0F, 1.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
        snout.setTextureOffset(0, 14).addCuboid(-3.0F, 6.0F, -2.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
        snout.setTextureOffset(36, 19).addCuboid(0.0F, 6.0F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
        CuboidModifier.removeFace(body, Direction.NORTH);
        CuboidModifier.removeFace(body, Direction.SOUTH);
        snout.setTextureOffset(30, 21).addCuboid(-3.0F, 6.0F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
        CuboidModifier.removeFace(body, Direction.NORTH);
        CuboidModifier.removeFace(body, Direction.SOUTH);
    }

    private ModelPart createBody(boolean top) {
        ModelPart body = new ModelPart(this);
        body.setPivot(0.0F, 24.0F, 0.0F);
        body.setTextureOffset(0, 14).addCuboid(-5.0F, -20.0F, -5.0F, 10.0F, 16.0F, 10.0F, 0.0F, false);
        CuboidModifier.removeFace(body, Direction.DOWN);
        body.setTextureOffset(0, 18).addCuboid(-1.0F, -22.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
        CuboidModifier.removeFace(body, Direction.DOWN);
        if (!top) {
            CuboidModifier.removeFace(body, Direction.UP);
        }
        body.setTextureOffset(0, 0).addCuboid(-5.0F, -4.0F, -5.0F, 10.0F, 4.0F, 10.0F, 0.0F, false);
        CuboidModifier.removeFace(body, Direction.UP);
        CuboidModifier.removeFace(body, Direction.WEST);
        body.setTextureOffset(0, 0).addCuboid(5.0F, -4.0F, -5.0F, 15.0F, 4.0F, 10.0F, 0.0F, false);
        CuboidModifier.removeFace(body, Direction.EAST);
        return body;
    }

    private ModelPart createLeftEyePole(boolean bottom) {
        ModelPart leftEyePole = new ModelPart(this);
        leftEyePole.setPivot(-2.0F, -10.0F, -3.0F);
        leftEyePole.setTextureOffset(34, 14).addCuboid(-1.0F, -6.0F, 0.0F, 1.0F, 6.0F, 1.0F, 0.0F, false);
        if (!bottom) {
            CuboidModifier.removeFace(leftEyePole, Direction.DOWN);
        }
        return leftEyePole;
    }

    private ModelPart createRightEyePole(boolean bottom) {
        ModelPart rightEyePole = new ModelPart(this);
        rightEyePole.setPivot(-2.0F, -10.0F, 3.0F);
        rightEyePole.setTextureOffset(30, 14).addCuboid(-1.0F, -6.0F, -1.0F, 1.0F, 6.0F, 1.0F, 0.0F, false);
        if (!bottom) {
            CuboidModifier.removeFace(rightEyePole, Direction.DOWN);
        }
        return rightEyePole;
    }

    @Override
    public Iterable<ModelPart> getParts() {
        return children;
    }

    @Override
    public void setAngles(SlugSlimeEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        children.clear();
        children.add(bodyNoTop);
        children.add(head);
        children.add(leftEyePoleNoBottom);
        children.add(rightEyePoleNoBottom);
        children.add(snout);
    }
}
