package polydungeons;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import polydungeons.block.DungeonDoorOpenerBlock;

public class PolyDungeonsEvents {
    public static void registerAll() {
        UseBlockCallback.EVENT.register((player, world, hand, hit) -> {
            if (world.getBlockState(hit.getBlockPos()).isOf(new DungeonDoorOpenerBlock())) {
                DungeonDoorOpenerBlock openerBlock = (DungeonDoorOpenerBlock) world.getBlockState(hit.getBlockPos()).getBlock();
                openerBlock.use(world, hit.getBlockPos(), player);
                return ActionResult.SUCCESS;
            } else {
                return ActionResult.PASS;
            }
        });
    }
}
