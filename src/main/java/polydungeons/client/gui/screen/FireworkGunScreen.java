package polydungeons.client.gui.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.TranslatableText;
import polydungeons.PolyDungeons;
import polydungeons.container.containers.FireworkGunContainer;
import spinnery.client.screen.BaseContainerScreen;
import spinnery.widget.*;
import spinnery.widget.api.Position;
import spinnery.widget.api.Size;

public class FireworkGunScreen extends BaseContainerScreen<FireworkGunContainer> {

	private final static int TOP_OFFSET = 24;
	private final static int SLOT_SIZE = 18;
	private final static int WIDTH_PADDING = 14;
	private final static int INVENTORY_LABEL_EXTRA = 8;

	public FireworkGunScreen(FireworkGunContainer linkedContainer, PlayerEntity player) {
		super(new TranslatableText("gui.polydungeons.ammo"), linkedContainer, player);
		int containerWidth = 9 * SLOT_SIZE + WIDTH_PADDING;

		WInterface mainInterface = getInterface();
		WPanel mainPanel = mainInterface.createChild(WPanel::new, Position.of(0, 0, 0), Size.of(containerWidth, INVENTORY_LABEL_EXTRA + 9 * SLOT_SIZE + 108));
		mainPanel.center();
		mainPanel.setOnAlign(WAbstractWidget::center);

		mainPanel.createChild(WStaticText::new).setText(new TranslatableText("item." + PolyDungeons.MODID + "firework_gun" + ".ammo")).setPosition(Position.of(mainPanel, 8, 6, 0));

		WSlot.addArray(Position.of(mainPanel, containerWidth / 2 - (SLOT_SIZE * 9) / 2, SLOT_SIZE, 1), Size.of(SLOT_SIZE, SLOT_SIZE), mainInterface, 0, FireworkGunContainer.AMMO, 9, 3);
		WSlot.addPlayerInventory(Position.of(mainPanel, containerWidth / 2 - (SLOT_SIZE * 9) / 2, INVENTORY_LABEL_EXTRA + 9 * SLOT_SIZE + TOP_OFFSET, 1), Size.of(SLOT_SIZE, SLOT_SIZE), mainInterface);
		mainPanel.createChild(WStaticText::new).setText(new TranslatableText("key.categories.inventory")).setPosition(Position.of(mainPanel, containerWidth / 2 - (SLOT_SIZE * 9) / 2, 9 * SLOT_SIZE + TOP_OFFSET - 4));
	}
}
