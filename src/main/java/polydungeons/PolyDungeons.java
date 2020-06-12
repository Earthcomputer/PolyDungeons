package polydungeons;

import net.fabricmc.api.ModInitializer;
import polydungeons.block.PolyDungeonsBlocks;
import polydungeons.entity.PolyDungeonsEntities;
import polydungeons.item.PolyDungeonsItems;

public class PolyDungeons implements ModInitializer {
    @Override
    public void onInitialize() {
        PolyDungeonsBlocks.registerAll();
        PolyDungeonsItems.registerAll();
        PolyDungeonsEntities.registerAll();
    }
}
