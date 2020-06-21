package polydungeons;

import net.fabricmc.api.ModInitializer;
import polydungeons.block.PolyDungeonsBlocks;
import polydungeons.block.entity.PolyDungeonsBlockEntities;
import polydungeons.container.PolyDungeonsContainers;
import polydungeons.entity.PolyDungeonsEntities;
import polydungeons.item.PolyDungeonsItems;
import polydungeons.loot.PolyDungeonsLootTables;
import polydungeons.network.PolyDungeonsServerNetwork;
import polydungeons.sound.PolyDungeonsSoundEvents;
import polydungeons.structures.DungeonData;

public class PolyDungeons implements ModInitializer {
    public static final String MODID = "polydungeons";

    @Override
    public void onInitialize() {
        PolyDungeonsBlocks.registerAll();
        PolyDungeonsBlockEntities.registerAll();
        PolyDungeonsItems.registerAll();
        PolyDungeonsEntities.registerAll();
        PolyDungeonsSoundEvents.registerAll();
        PolyDungeonsContainers.registerAll();
        PolyDungeonsServerNetwork.registerPackets();

        PolyDungeonsLootTables.registerAll();

        DungeonData.init();
        PolyDungeonsEvents.registerAll();
    }
}
