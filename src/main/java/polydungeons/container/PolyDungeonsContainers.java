package polydungeons.container;

import net.fabricmc.fabric.api.container.ContainerProviderRegistry;

public class PolyDungeonsContainers {

	public static void registerAll() {
		ContainerProviderRegistry.INSTANCE.registerFactory(FireworkLauncherContainer.IDENTIFIER,
				(syncId, id, player, buf) -> new FireworkLauncherContainer(syncId, buf.readText(), player.inventory));
	}
}
