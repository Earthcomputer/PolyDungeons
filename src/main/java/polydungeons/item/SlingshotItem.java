package polydungeons.item;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import polydungeons.entity.SlingshotProjectileEntity;
import polydungeons.sound.PolyDungeonsSoundEvents;

import java.util.function.Predicate;

public class SlingshotItem extends RangedWeaponItem {
    public SlingshotItem(Settings settings) {
        super(settings);
        EnchantmentApplicableCallback.EVENT.register((enchantment, stack, enchantmentTable) -> {
            if (stack.getItem() == this) {
                if (enchantment == Enchantments.INFINITY || (enchantment == Enchantments.FLAME && !enchantmentTable)) {
                    return ActionResult.SUCCESS;
                }
            }
            return ActionResult.PASS;
        });
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (findAmmo(user, stack).isEmpty()) {
            return TypedActionResult.fail(stack);
        } else {
            user.setCurrentHand(hand);
            return TypedActionResult.consume(stack);
        }
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (!(user instanceof PlayerEntity)) {
            return;
        }

        PlayerEntity player = (PlayerEntity) user;
        boolean infinity = player.abilities.creativeMode || EnchantmentHelper.getLevel(Enchantments.INFINITY, stack) > 0;
        ItemStack ammo = findAmmo(player, stack);
        if (ammo.isEmpty()) {
            return;
        }

        int useTicks = getMaxUseTime(stack) - remainingUseTicks;
        float pullProgress = getPullProgress(useTicks);
        if (pullProgress < 0.1) {
            return;
        }

        if (!world.isClient) {
            SlingshotProjectileEntity projectile = new SlingshotProjectileEntity(player, world);
            projectile.setProperties(player, player.pitch, player.yaw, 0, pullProgress * 3, 1);
            if (ammo.getItem() == Items.MAGMA_CREAM) {
                projectile.setFiery(true);
            }
            world.spawnEntity(projectile);

            stack.damage(1, player, p -> p.sendToolBreakStatus(player.getActiveHand()));
        }

        world.playSound(null, player.getX(), player.getY(), player.getZ(), PolyDungeonsSoundEvents.SLINGSHOT, SoundCategory.PLAYERS, 1, 1f / (RANDOM.nextFloat() * 0.4f + 1.2f) + pullProgress * 0.5f);

        if (!infinity) {
            ammo.decrement(1);
            if (ammo.isEmpty()) {
                player.inventory.removeOne(ammo);
            }
        }

        player.incrementStat(Stats.USED.getOrCreateStat(this));
    }

    private ItemStack findAmmo(PlayerEntity player, ItemStack stack) {
        ItemStack ammo = player.getArrowType(stack);
        if (ammo.isEmpty() || !getProjectiles().test(ammo)) {
            if (player.abilities.creativeMode) {
                if (EnchantmentHelper.getLevel(Enchantments.FLAME, stack) > 0) {
                    return new ItemStack(Items.MAGMA_CREAM);
                } else {
                    return new ItemStack(Items.SLIME_BALL);
                }
            } else {
                return ItemStack.EMPTY; // has no ammo
            }
        }
        return ammo;
    }

    public static float getPullProgress(float useTicks) {
        float progress = useTicks / 20f;
        progress = (progress * progress + progress * 2f) / 3f;
        if (progress > 1) {
            progress = 1;
        }
        return progress;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }

    @Override
    public Predicate<ItemStack> getProjectiles() {
        return stack -> stack.getItem() == Items.SLIME_BALL || stack.getItem() == Items.MAGMA_CREAM;
    }

    @Override
    public int getRange() {
        return 15; // idk used for AI
    }
}
