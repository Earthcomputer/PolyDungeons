package polydungeons.block.entity;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.EndPortalBlockEntity;
import net.minecraft.util.math.Direction;

public class DecorativeEndBlockEntity extends EndPortalBlockEntity {
    public DecorativeEndBlockEntity() {
        super(PolyDungeonsBlockEntities.END_BLOCK);
    }

    @Override
    public boolean shouldDrawSide(Direction direction) {
        return true;
    }


}
