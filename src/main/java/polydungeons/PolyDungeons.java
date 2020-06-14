package polydungeons;

import net.fabricmc.api.ModInitializer;
import polydungeons.block.PolyDungeonsBlocks;
import polydungeons.container.PolyDungeonsContainers;
import polydungeons.entity.PolyDungeonsEntities;
import polydungeons.item.PolyDungeonsItems;
import polydungeons.network.PolyDungeonsServerNetwork;
import polydungeons.sound.PolyDungeonsSoundEvents;
import polydungeons.structures.DungeonData;

public class PolyDungeons implements ModInitializer {
    public static final String MODID = "polydungeons";

    @Override
    public void onInitialize() {
        PolyDungeonsBlocks.registerAll();
        PolyDungeonsItems.registerAll();
        PolyDungeonsEntities.registerAll();
        PolyDungeonsSoundEvents.registerAll();
        PolyDungeonsContainers.registerAll();
        PolyDungeonsServerNetwork.registerPackets();

        DungeonData.init();
        PolyDungeonsEvents.registerAll();
    }
}
