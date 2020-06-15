package polydungeons.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ModelPart.Cuboid.class)
@Environment(EnvType.CLIENT)
public interface ModelPartCuboidAccessor {
    @Accessor
    ModelPart.Quad[] getSides();

    @Mutable
    @Accessor
    void setSides(ModelPart.Quad[] sides);
}
