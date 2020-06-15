package polydungeons.client.gui.screen;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import polydungeons.container.FireworkLauncherContainer;
import polydungeons.tag.PolyDungeonsItemTags;
import spinnery.client.screen.BaseContainerScreen;
import spinnery.widget.*;
import spinnery.widget.api.Position;
import spinnery.widget.api.Size;

public class FireworkLauncherScreen extends BaseContainerScreen<FireworkLauncherContainer> {

	public FireworkLauncherScreen(FireworkLauncherContainer container) {
		super(container.name, container, container.player);
//
//		WInterface mainInterface = getInterface();
//		WPanel mainPanel = mainInterface.createChild(WPanel::new, Position.of(0, 0, 0), Size.of(9 * 18 + 8, 3 * 18 + 108)).setParent(mainInterface);
//
//		mainPanel.setLabel(container.name);
//		mainPanel.setOnAlign(WAbstractWidget::center);
//		mainPanel.center();
//
//		WSlot.addPlayerInventory(Position.of(mainPanel, ((mainPanel.getWidth()) / 2) - (int) (18 * 4.5f), 3 * 18 + 24, 1), Size.of(18, 18), mainInterface);
//
//		WSlot slot = mainInterface.createChild(WSlot::new,
//				Position.of(mainPanel, 18, 18, 0), Size.of(18, 18))
//				.setSlotNumber(0).setInventoryNumber(FireworkLauncherContainer.AMMO)
////				.setWhitelist().accept(PolyDungeonsItemTags.FIREWORK_LAUNCHER_AMMO);
//		;
//		mainPanel.add(slot);
//
//		WItem item = new WItem().setStack(new ItemStack(Items.GUNPOWDER))
//				.setPosition(Position.of(mainPanel, 18, 36, 0))
//				.setSize(Size.of(36, 36));
//		mainPanel.add(item);
//
//
////		WSlot.addArray(Position.of(mainPanel, 4, 19, 1), Size.of(18, 18), mainInterface, 0, FireworkLauncherContainer.AMMO, 9, 3);
//
//		mainInterface.add(mainPanel);
		WInterface mainInterface = getInterface();
		WPanel mainPanel = mainInterface.createChild(WPanel::new, Position.of(0, 0, 0), Size.of(9 * 18 + 8, 3 * 18 + 108)).setParent(mainInterface);
		mainPanel.setLabel(container.name);

		mainPanel.setOnAlign(WAbstractWidget::center);

		mainPanel.center();

		mainInterface.add(mainPanel);

		WSlot.addPlayerInventory(Position.of(mainPanel, ((mainPanel.getWidth()) / 2) - (int) (18 * 4.5f), 3 * 18 + 24, 1), Size.of(18, 18), mainInterface);
		WSlot.addArray(Position.of(mainPanel, 4, 19, 1), Size.of(18, 18), mainInterface, 0, 1, 9, 3);
	}
}
