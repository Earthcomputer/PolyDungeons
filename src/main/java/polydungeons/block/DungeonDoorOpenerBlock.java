package polydungeons.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DungeonDoorOpenerBlock extends Block {

    public DungeonDoorOpenerBlock() {
        super(Settings.of(Material.STONE, MaterialColor.RED)
                .requiresTool().strength(50.0F, 1200.0F)
                .sounds(BlockSoundGroup.NETHER_BRICKS));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        for (BlockPos blockPos : BlockPos.iterateOutwards(pos, 3, 3, 3)) {
            if (world.getBlockState(blockPos).isOf(PolyDungeonsBlocks.DUNGEON_DOOR)) {
                DungeonDoorBlock doorBlock = (DungeonDoorBlock) world.getBlockState(blockPos).getBlock();
                doorBlock.doorOpened(world, blockPos);
            }
        }
        return ActionResult.SUCCESS;
    }

    public DungeonDoorOpenerBlock(Settings settings) {
        super(settings);
    }
}
