package polydungeons.container.containers;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import polydungeons.item.FireworkLauncherItem;
import spinnery.common.container.BaseContainer;
import spinnery.common.inventory.BaseInventory;
import spinnery.widget.WAbstractWidget;
import spinnery.widget.WInterface;
import spinnery.widget.WSlot;
import spinnery.widget.api.Action;

public class FireworkGunContainer extends BaseContainer {
	public static final int AMMO = 1;

	public FireworkGunContainer(int synchronizationID, Identifier identifier, PlayerEntity player, PacketByteBuf packetByteBuf) {
		super(synchronizationID, player.inventory);
		ItemStack stack = player.getStackInHand(player.getActiveHand());

		if(stack.getItem() instanceof FireworkLauncherItem) {
			FireworkLauncherItem item = (FireworkLauncherItem) stack.getItem();
			setupInventory(stack);
			WInterface mainInterface = setupContainer(item);
			lockSlots(mainInterface);
		} else {
			this.close(player);
		}
	}

	private void setupInventory(ItemStack stack) {
		getInventories().put(AMMO, new BaseInventory(27));
	}

	private WInterface setupContainer(FireworkLauncherItem item) {
		WInterface mainInterface = getInterface();
		WSlot.addHeadlessArray(mainInterface, 0, AMMO, 27, 3);
		WSlot.addHeadlessPlayerInventory(mainInterface);
		return mainInterface;
	}

	private void lockSlots(WInterface mainInterface) {
		for (WAbstractWidget widget : mainInterface.getWidgets()) {
			if (widget instanceof WSlot) {
				WSlot slot = (WSlot) widget;

				if(slot.getStack().getItem() instanceof FireworkLauncherItem) {
					((WSlot) widget).setLocked(true);
				}
			}
		}
	}

	@Override
	public void onSlotAction(int slotNumber, int inventoryNumber, int button, Action action, PlayerEntity player) {
		for (WAbstractWidget widget : this.serverInterface.getAllWidgets()) {
			if (widget instanceof WSlot && ((WSlot) widget).getSlotNumber() == slotNumber && ((WSlot) widget).getInventoryNumber() == inventoryNumber) {
				if (!(((WSlot) widget).getStack().getItem() instanceof FireworkLauncherItem)) {
					super.onSlotAction(slotNumber, inventoryNumber, button, action, player);
				}
			}
		}
	}
}
