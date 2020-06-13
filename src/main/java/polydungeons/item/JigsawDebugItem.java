package polydungeons.item;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.JigsawBlockEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class JigsawDebugItem extends Item {
    public JigsawDebugItem() {
        super(new Settings().maxCount(1));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if(!world.isClient) {
            BlockEntity target = world.getBlockEntity(context.getBlockPos());
            ItemStack stack = context.getStack();
            CompoundTag tag = stack.getOrCreateTag();
            if(target instanceof JigsawBlockEntity) {
                ((JigsawBlockEntity)target).generate((ServerWorld)world, tag.getInt("depth"), tag.getBoolean("keepJigsaw"));
            } else {
                PlayerEntity player = context.getPlayer();
                if(player.isSneaking()) {
                    int depth = tag.getInt("depth");
                    if(depth == 20)
                        depth = 0;
                    else
                        depth++;

                    player.sendMessage(new LiteralText("Depth: " + depth), true);
                    tag.putInt("depth", depth);
                } else {
                    boolean keepJigsaw = tag.getBoolean("keepJigsaw");
                    keepJigsaw = !keepJigsaw;

                    player.sendMessage(new LiteralText("Keep Jigsaws: " + keepJigsaw), true);
                    tag.putBoolean("keepJigsaw", keepJigsaw);
                }
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public boolean damage(DamageSource source) {
        if(source.getSource() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity)source.getSource();
            ItemStack stack = player.getStackInHand(Hand.MAIN_HAND);

        }
        return super.damage(source);
    }
}
