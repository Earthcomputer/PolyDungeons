package polydungeons.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import polydungeons.client.ber.PolyDungeonsBlockEntityRenderers;
import polydungeons.client.entity.EntityClientInit;
import polydungeons.client.gui.screen.PolyDungeonsScreens;
import polydungeons.client.network.PolyDungeonsClientNetwork;

@Environment(EnvType.CLIENT)
public class PolyDungeonsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        PolyDungeonsBlockEntityRenderers.init();
        EntityClientInit.registerAll();
        PolyDungeonsClientNetwork.registerPackets();

        PolyDungeonsScreens.registerAll();
    }
}
