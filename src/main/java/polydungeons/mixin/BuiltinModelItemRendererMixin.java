package polydungeons.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import polydungeons.block.entity.relic.SamplerRelicBlockEntity;
import polydungeons.block.relic.SamplerRelicBlock;

@Mixin(BuiltinModelItemRenderer.class)
@Environment(EnvType.CLIENT)
public class BuiltinModelItemRendererMixin {
    @Unique
    private SamplerRelicBlockEntity renderSamplerRelic = new SamplerRelicBlockEntity();

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void onRender(ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j, CallbackInfo ci) {
        if (stack.getItem() instanceof BlockItem) {
            Block block = ((BlockItem) stack.getItem()).getBlock();
            BlockEntity be;

            if (block instanceof SamplerRelicBlock) {
                be = renderSamplerRelic;
            } else {
                return;
            }

            BlockEntityRenderDispatcher.INSTANCE.renderEntity(be, matrixStack, vertexConsumerProvider, i, j);
            ci.cancel();
        }
    }
}
