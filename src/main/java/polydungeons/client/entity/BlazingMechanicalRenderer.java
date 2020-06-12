package polydungeons.client.entity;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import polydungeons.PolyDungeons;
import polydungeons.entity.BlazingMechanicalEntity;

public class BlazingMechanicalRenderer extends MobEntityRenderer<BlazingMechanicalEntity, BlazingMechanicalModel> {
    public BlazingMechanicalRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new BlazingMechanicalModel(), 0.5f);
    }

    @Override
    public Identifier getTexture(BlazingMechanicalEntity entity) {
        return new Identifier(PolyDungeons.MODID, "textures/entity/blazing_mechanical/blazing_mechanical.png");
    }
}
