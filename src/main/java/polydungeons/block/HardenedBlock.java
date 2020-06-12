package polydungeons.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.sound.BlockSoundGroup;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HardenedBlock extends Block {

    public HardenedBlock() {
        super(Settings.of(Material.STONE, MaterialColor.RED)
                .requiresTool().strength(50.0F, 1200.0F)
                .sounds(BlockSoundGroup.NETHER_BRICKS));
    }

    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
        return Collections.singletonList(new ItemStack(this));
    }
}
