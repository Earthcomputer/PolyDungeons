package polydungeons.block.relic;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.BlockView;
import polydungeons.PolyDungeons;
import polydungeons.block.entity.relic.SamplerRelicBlockEntity;

public abstract class RelicBlock  extends Block {
    public RelicBlock() {
        super(FabricBlockSettings.of(Material.METAL).nonOpaque().hardness(10f).drops(new Identifier(PolyDungeons.MODID, "blocks/relic")));
    }
}