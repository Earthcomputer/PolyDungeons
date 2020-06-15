package polydungeons.client.entity.model;

import it.unimi.dsi.fastutil.objects.ObjectList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.math.Direction;
import org.apache.commons.lang3.ArrayUtils;
import polydungeons.mixin.ModelPartAccessor;
import polydungeons.mixin.ModelPartCuboidAccessor;

@Environment(EnvType.CLIENT)
public class CuboidModifier {

    private static ModelPart.Cuboid getLastCuboid(ModelPart part) {
        ObjectList<ModelPart.Cuboid> cuboids = ((ModelPartAccessor) part).getCuboids();
        if (cuboids.isEmpty()) {
            throw new IllegalStateException("Model part has no cuboids");
        }
        return cuboids.get(cuboids.size() - 1);
    }

    public static void removeFace(ModelPart part, Direction face) {
        Vector3f faceVector = face.getOpposite().getUnitVector();
        ModelPart.Cuboid cuboid = getLastCuboid(part);
        ModelPart.Quad[] sides = ((ModelPartCuboidAccessor) cuboid).getSides();
        for (int i = 0; i < sides.length; i++) {
            if (sides[i].direction.equals(faceVector)) {
                sides = ArrayUtils.remove(sides, i);
                i--;
            }
        }
        ((ModelPartCuboidAccessor) cuboid).setSides(sides);
    }

}
