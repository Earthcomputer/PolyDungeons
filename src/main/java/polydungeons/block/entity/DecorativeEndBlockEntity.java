package polydungeons.block.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.entity.EndPortalBlockEntity;
import net.minecraft.util.math.Direction;

public class DecorativeEndBlockEntity extends EndPortalBlockEntity {
    public DecorativeEndBlockEntity() {
        super(PolyDungeonsBlockEntities.END_BLOCK);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public boolean shouldDrawSide(Direction direction) {
        // Cull faces that are not visible
        assert world != null;
        // TODO: cache these values
        return Block.shouldDrawSide(getCachedState(), world, getPos(), direction);
    }


}
