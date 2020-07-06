package polydungeons.client.ber;

import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.EndPortalBlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Matrix4f;
import polydungeons.block.entity.DecorativeEndBlockEntity;

import java.util.Random;

public class SolidEndPortalBlockEntityRenderer extends EndPortalBlockEntityRenderer<DecorativeEndBlockEntity> {
    // Internal version of RANDOM, as EndPortalBlockEntityRenderer doesn't expose it
    private static final Random RANDOM = new Random(31100L);

    // Self contained buffers for batching draw calls per layer
    private static final RenderLayer[] RENDER_LAYERS = new RenderLayer[16];
    private static final BufferBuilder[] BUFFER_BUILDERS = new BufferBuilder[16];

    static {
        for (int i = 0; i < 16; i++) {
            // Initialise each buffer and layer
            RenderLayer layer = RenderLayer.getEndPortal(i + 1);
            BufferBuilder builder = new BufferBuilder(layer.getExpectedBufferSize());
            builder.begin(layer.getDrawMode(), layer.getVertexFormat());
            RENDER_LAYERS[i] = layer;
            BUFFER_BUILDERS[i] = builder;
        }
    }

    public SolidEndPortalBlockEntityRenderer(BlockEntityRenderDispatcher blockEntityRenderDispatcher) {
        super(blockEntityRenderDispatcher);
    }

    private static boolean wasRendered = false;

    /**
     * Draws the buffer builders for each render layer to the screen.
     * Should be called after all BER render methods have been called.
     */
    public static void drawBuffers() {
        if (wasRendered) {
            // Should only be run if render has been called at least once
            wasRendered = false;
            for (int i = 0; i < 16; i++) {
                RenderLayer layer = RENDER_LAYERS[i];
                BufferBuilder buf = BUFFER_BUILDERS[i];
                layer.draw(buf, 0, 0, 0);
                // Set up the buffer builder to be ready to accept vertices again
                buf.begin(layer.getDrawMode(), layer.getVertexFormat());
            }
        }
    }

    public void render(DecorativeEndBlockEntity endPortalBlockEntity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j) {
        wasRendered = true;
        RANDOM.setSeed(31100L);
        double distanceToCamera = endPortalBlockEntity.getPos().getSquaredDistance(this.dispatcher.camera.getPos(), true);
        int layerCount = this.method_3592(distanceToCamera);
        // Not sure on this name? Doesn't matter anyway, we override it to 1.0F
        float depth = this.method_3594();
        Matrix4f matrix4f = matrixStack.peek().getModel();

        renderCube(endPortalBlockEntity, depth, 0.15F, matrix4f, BUFFER_BUILDERS[0]);
        for(int l = 1; l < layerCount; ++l) {
            renderCube(endPortalBlockEntity, depth, 2.0F / (float)(18 - l), matrix4f, BUFFER_BUILDERS[l]);
        }
    }

    private void renderCube(DecorativeEndBlockEntity endPortalBlockEntity, float depth, float g, Matrix4f matrix4f, VertexConsumer vertexConsumer) {
        float red = (RANDOM.nextFloat() * 0.5F + 0.1F) * g;
        float green = (RANDOM.nextFloat() * 0.5F + 0.4F) * g;
        float blue = (RANDOM.nextFloat() * 0.5F + 0.5F) * g;
        renderFace(endPortalBlockEntity, matrix4f, vertexConsumer, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, red, green, blue, Direction.SOUTH);
        renderFace(endPortalBlockEntity, matrix4f, vertexConsumer, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, red, green, blue, Direction.NORTH);
        renderFace(endPortalBlockEntity, matrix4f, vertexConsumer, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, red, green, blue, Direction.EAST);
        renderFace(endPortalBlockEntity, matrix4f, vertexConsumer, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, red, green, blue, Direction.WEST);
        renderFace(endPortalBlockEntity, matrix4f, vertexConsumer, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, red, green, blue, Direction.DOWN);
        renderFace(endPortalBlockEntity, matrix4f, vertexConsumer, 0.0F, 1.0F, depth, depth, 1.0F, 1.0F, 0.0F, 0.0F, red, green, blue, Direction.UP);
    }

    private void renderFace(DecorativeEndBlockEntity endPortalBlockEntity, Matrix4f matrix4f, VertexConsumer vertexConsumer, float f, float g, float h, float i, float j, float k, float l, float m, float n, float o, float p, Direction direction) {
        if (endPortalBlockEntity.shouldDrawSide(direction)) {
            vertexConsumer.vertex(matrix4f, f, h, j).color(n, o, p, 1.0F).next();
            vertexConsumer.vertex(matrix4f, g, h, k).color(n, o, p, 1.0F).next();
            vertexConsumer.vertex(matrix4f, g, i, l).color(n, o, p, 1.0F).next();
            vertexConsumer.vertex(matrix4f, f, i, m).color(n, o, p, 1.0F).next();
        }
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
