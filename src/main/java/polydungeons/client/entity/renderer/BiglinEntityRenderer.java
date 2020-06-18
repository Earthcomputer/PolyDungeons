package polydungeons.client.entity.renderer;

import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PiglinEntityModel;
import net.minecraft.util.Identifier;
import polydungeons.entity.BiglinEntity;

public class BiglinEntityRenderer extends BipedEntityRenderer<BiglinEntity, PiglinEntityModel<BiglinEntity>> {
    private static final Identifier PIGLIN_TEXTURE = new Identifier("textures/entity/piglin/piglin.png");

    public BiglinEntityRenderer(EntityRenderDispatcher dispatcher) {
        super(
                dispatcher,
                new PiglinEntityModel(3.0f, 64, 64),
                0.5f,
                1.0019531f,
                1.0f,
                1.0019531f
        );
        this.addFeature(new ArmorFeatureRenderer(this, new BipedEntityModel(0.5F), new BipedEntityModel(1.02F)));
    }

    @Override
    public Identifier getTexture(BiglinEntity entity) {
        return PIGLIN_TEXTURE;
    }
}
