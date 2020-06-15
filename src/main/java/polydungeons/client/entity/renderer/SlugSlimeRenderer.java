package polydungeons.client.entity.renderer;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import polydungeons.PolyDungeons;
import polydungeons.client.entity.model.SlugSlimeModel;
import polydungeons.entity.SlugSlimeEntity;

public class SlugSlimeRenderer extends MobEntityRenderer<SlugSlimeEntity, SlugSlimeModel> {
    private static final Identifier SLIME_TEXTURE = new Identifier(PolyDungeons.MODID, "textures/entity/slug_slime.png");

    public SlugSlimeRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new SlugSlimeModel(), 0.5f);
    }

    @Override
    public Identifier getTexture(SlugSlimeEntity entity) {
        return SLIME_TEXTURE;
    }
}
