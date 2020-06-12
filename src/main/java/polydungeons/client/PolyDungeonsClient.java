package polydungeons.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import polydungeons.client.entity.EntityClientInit;
import polydungeons.client.network.PolyDungeonsClientNetwork;

@Environment(EnvType.CLIENT)
public class PolyDungeonsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityClientInit.registerAll();
        PolyDungeonsClientNetwork.registerPackets();
    }
}
