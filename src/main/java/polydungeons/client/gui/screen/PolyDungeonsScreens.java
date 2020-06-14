package polydungeons.client.gui.screen;

import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import polydungeons.container.FireworkLauncherContainer;

public class PolyDungeonsScreens {
	public static void registerAll() {
		ScreenProviderRegistry.INSTANCE.registerFactory(FireworkLauncherContainer.IDENTIFIER,
				FireworkLauncherScreen::new);
	}
}
