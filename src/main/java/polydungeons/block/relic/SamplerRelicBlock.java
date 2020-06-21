package polydungeons.block.relic;

import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;
import polydungeons.block.entity.relic.SamplerRelicBlockEntity;

public class SamplerRelicBlock extends RelicBlock implements BlockEntityProvider {
    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new SamplerRelicBlockEntity();
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }
}
