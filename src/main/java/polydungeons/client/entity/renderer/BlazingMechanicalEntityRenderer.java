package polydungeons.client.entity.renderer;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import polydungeons.PolyDungeons;
import polydungeons.client.entity.model.BlazingMechanicalModel;
import polydungeons.entity.BlazingMechanicalEntity;

public class BlazingMechanicalEntityRenderer extends MobEntityRenderer<BlazingMechanicalEntity, BlazingMechanicalModel> {
    public BlazingMechanicalEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new BlazingMechanicalModel(), 0.5f);
    }

    @Override
    public Identifier getTexture(BlazingMechanicalEntity entity) {
        return new Identifier(PolyDungeons.MODID, "textures/entity/blazing_mechanical/blazing_mechanical.png");
    }

    @Override
    protected int getBlockLight(BlazingMechanicalEntity entity, BlockPos blockPos) {
        return 15;
    }
}
