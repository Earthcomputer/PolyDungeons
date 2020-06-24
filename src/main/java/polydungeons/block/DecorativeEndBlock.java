package polydungeons.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;
import polydungeons.block.entity.DecorativeEndBlockEntity;

public class DecorativeEndBlock extends Block implements BlockEntityProvider {
    public DecorativeEndBlock() {
        super(FabricBlockSettings.of(Material.STONE).hardness(10f));
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new DecorativeEndBlockEntity();
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }
}
