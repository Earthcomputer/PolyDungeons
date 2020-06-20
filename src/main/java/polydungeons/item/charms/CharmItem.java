package polydungeons.item.charms;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public abstract class CharmItem extends Item {
    public CharmItem() {
        super(new Settings().maxCount(16));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(!world.isClient) {
            if (createEntity(user, user.getPos()))
                user.getStackInHand(hand).decrement(1);
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if(!context.getWorld().isClient) {
            if (createEntity(context.getPlayer(), context.getHitPos()))
                context.getStack().decrement(1);
        }
        return ActionResult.SUCCESS;
    }

    abstract boolean createEntity(PlayerEntity creator, Vec3d pos);
}
