package polydungeons.client.entity.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector4f;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import polydungeons.entity.SlugSlimeEntity;

import java.util.Arrays;
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
    private final List<ModelPart> children;
    private static final int HEAD_INDEX = 0;
    private static final int BODY_INDEX = 1;
    private static final int LEFT_EYE_POLE_INDEX = 2;
    private static final int RIGHT_EYE_POLE_INDEX = 3;
    private static final int SNOUT_INDEX = 4;
    private static final int PART_COUNT = 5;

    private static final float DEFAULT_LEFT_EYE_X = 3;
    private static final float DEFAULT_LEFT_EYE_Z = -3;
    private static final float DEFAULT_RIGHT_EYE_X = -3;
    private static final float DEFAULT_RIGHT_EYE_Z = -3;
    private static final float DEFAULT_EYES_Y = -10;
    private static final float DEFAULT_SNOUT_X = 0;
    private static final float DEFAULT_SNOUT_Z = -3;
    private static final float DEFAULT_SNOUT_Y = -7;

    public SlugSlimeModel() {
        super(RenderLayer::getEntityTranslucent);

        textureWidth = 128;
        textureHeight = 128;

        children = Arrays.asList(new ModelPart[PART_COUNT]);

        body = createBody(true);
        bodyNoTop = createBody(false);
        children.set(BODY_INDEX, body);

        head = new ModelPart(this);
        head.setPivot(0.0F, 2.0F, 0.0F);
        head.setTextureOffset(0, 0).addCuboid(-3.0F, -12.0F, -3.0F, 6.0F, 12.0F, 6.0F, 0.0F, false);
        children.set(HEAD_INDEX, head);

        leftEyePole = createLeftEyePole(true);
        leftEyePoleNoBottom = createLeftEyePole(false);
        children.set(LEFT_EYE_POLE_INDEX, leftEyePole);

        rightEyePole = createRightEyePole(true);
        rightEyePoleNoBottom = createRightEyePole(false);
        children.set(RIGHT_EYE_POLE_INDEX, rightEyePole);

        snout = new ModelPart(this);
        snout.setPivot(DEFAULT_SNOUT_X, DEFAULT_SNOUT_Y, DEFAULT_SNOUT_Z);
        snout.setTextureOffset(0, 29).addCuboid(-1.0F, 0.0F, -2.0F, 2.0F, 7.0F, 2.0F, 0.0F, false);
        snout.setTextureOffset(10, 18).addCuboid(-2.0F, 7.0F, -3.0F, 1.0F, 1.0F, 4.0F, 0.0F, false);
        snout.setTextureOffset(0, 18).addCuboid(1.0F, 7.0F, -3.0F, 1.0F, 1.0F, 4.0F, 0.0F, false);
        snout.setTextureOffset(0, 2).addCuboid(-1.0F, 7.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        CuboidModifier.removeFace(snout, Direction.WEST);
        CuboidModifier.removeFace(snout, Direction.EAST);
        snout.setTextureOffset(0, 0).addCuboid(-1.0F, 7.0F, -3.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
        CuboidModifier.removeFace(snout, Direction.WEST);
        CuboidModifier.removeFace(snout, Direction.EAST);
        children.set(SNOUT_INDEX, snout);
    }

    private ModelPart createBody(boolean top) {
        ModelPart body = new ModelPart(this);
        body.setPivot(0.0F, 24.0F, 0.0F);
        body.setTextureOffset(0, 29).addCuboid(-5.0F, -20.0F, -5.0F, 10.0F, 16.0F, 10.0F, 0.0F, false);
        CuboidModifier.removeFace(body, Direction.DOWN);
        body.setTextureOffset(6, 18).addCuboid(-1.0F, -22.0F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
        CuboidModifier.removeFace(body, Direction.DOWN);
        if (!top) {
            CuboidModifier.removeFace(body, Direction.UP);
        }
        body.setTextureOffset(0, 29).addCuboid(-5.0F, -4.0F, -5.0F, 10.0F, 4.0F, 10.0F, 0.0F, false);
        CuboidModifier.removeFace(body, Direction.UP);
        CuboidModifier.removeFace(body, Direction.NORTH);
        body.setTextureOffset(10, 10).addCuboid(-5.0F, -4.0F, 5.0F, 10.0F, 4.0F, 15.0F, 0.0F, false);
        CuboidModifier.removeFace(body, Direction.SOUTH);
        return body;
    }

    private ModelPart createLeftEyePole(boolean bottom) {
        ModelPart leftEyePole = new ModelPart(this);
        leftEyePole.setPivot(DEFAULT_LEFT_EYE_X, DEFAULT_EYES_Y, DEFAULT_LEFT_EYE_Z);
        leftEyePole.setTextureOffset(30, 30).addCuboid(-1.0F, -6.0F, 0.0F, 1.0F, 6.0F, 1.0F, 0.0F, false);
        if (!bottom) {
            CuboidModifier.removeFace(leftEyePole, Direction.DOWN);
        }
        return leftEyePole;
    }

    private ModelPart createRightEyePole(boolean bottom) {
        ModelPart rightEyePole = new ModelPart(this);
        rightEyePole.setPivot(DEFAULT_RIGHT_EYE_X, DEFAULT_EYES_Y, DEFAULT_RIGHT_EYE_Z);
        rightEyePole.setTextureOffset(20, 18).addCuboid(0.0F, -6.0F, 0.0F, 1.0F, 6.0F, 1.0F, 0.0F, false);
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
    public void animateModel(SlugSlimeEntity entity, float limbAngle, float limbDistance, float tickDelta) {
        super.animateModel(entity, limbAngle, limbDistance, tickDelta);
        float headPitch = MathHelper.lerpAngleDegrees(tickDelta, entity.prevPitch, entity.pitch);
        float leftEyeYaw = entity.getLeftEyeYaw(tickDelta);
        float leftEyePitch = entity.getLeftEyePitch(tickDelta);
        float rightEyeYaw = entity.getRightEyeYaw(tickDelta);
        float rightEyePitch = entity.getRightEyePitch(tickDelta);
        float snoutYaw = entity.getSnoutYaw(tickDelta);
        float snoutPitch = entity.getSnoutPitch(tickDelta);

        ModelPart leftEyePole = leftEyePitch == headPitch ? this.leftEyePoleNoBottom : this.leftEyePole;
        ModelPart rightEyePole = rightEyePitch == headPitch ? this.rightEyePoleNoBottom : this.rightEyePole;
        leftEyePole.copyPositionAndRotation(children.get(LEFT_EYE_POLE_INDEX));
        rightEyePole.copyPositionAndRotation(children.get(RIGHT_EYE_POLE_INDEX));
        children.set(LEFT_EYE_POLE_INDEX, leftEyePole);
        children.set(RIGHT_EYE_POLE_INDEX, rightEyePole);

        leftEyePole.yaw = (float)Math.toRadians(leftEyeYaw);
        leftEyePole.pitch = (float)Math.toRadians(leftEyePitch);
        rightEyePole.yaw = (float)Math.toRadians(rightEyeYaw);
        rightEyePole.pitch = (float)Math.toRadians(rightEyePitch);
        snout.yaw = (float)Math.toRadians(snoutYaw);
        snout.pitch = (float)Math.toRadians(snoutPitch);
    }

    @Override
    public void setAngles(SlugSlimeEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        head.yaw = (float)Math.toRadians(headYaw);
        head.pitch = (float)Math.toRadians(headPitch);
        children.set(BODY_INDEX, headPitch == 0 ? bodyNoTop : body);
        ModelPart leftEyePole = children.get(LEFT_EYE_POLE_INDEX);
        ModelPart rightEyePole = children.get(RIGHT_EYE_POLE_INDEX);

        MatrixStack headTransform = new MatrixStack();
        head.rotate(headTransform); // does translation as well
        Vector4f leftEyePos = new Vector4f(DEFAULT_LEFT_EYE_X, DEFAULT_EYES_Y, DEFAULT_LEFT_EYE_Z, 1);
        leftEyePos.transform(headTransform.peek().getModel());
        Vector4f rightEyePos = new Vector4f(DEFAULT_RIGHT_EYE_X, DEFAULT_EYES_Y, DEFAULT_RIGHT_EYE_Z, 1);
        rightEyePos.transform(headTransform.peek().getModel());
        Vector4f snoutPos = new Vector4f(DEFAULT_SNOUT_X, DEFAULT_SNOUT_Y, DEFAULT_SNOUT_Z, 1);
        snoutPos.transform(headTransform.peek().getModel());

        leftEyePole.setPivot(leftEyePos.getX(), leftEyePos.getY(), leftEyePos.getZ());
        rightEyePole.setPivot(rightEyePos.getX(), rightEyePos.getY(), rightEyePos.getZ());
        snout.setPivot(snoutPos.getX(), snoutPos.getY(), snoutPos.getZ());
    }
}
