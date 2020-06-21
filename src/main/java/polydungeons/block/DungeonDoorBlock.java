package polydungeons.block;

import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DungeonDoorBlock extends Block {
    public DungeonDoorBlock() {
        super(Settings.of(Material.STONE, MaterialColor.RED)
                .requiresTool().strength(50.0F, 1200.0F)
                .sounds(BlockSoundGroup.NETHER_BRICKS));
    }
    public void doorOpened(World world, BlockPos pos) {
        if (!world.isClient) {
            world.breakBlock(pos, false);
        }
    }

}
