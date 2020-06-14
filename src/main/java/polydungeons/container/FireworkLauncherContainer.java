package polydungeons.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import polydungeons.PolyDungeons;
import polydungeons.tag.PolyDungeonsItemTags;
import spinnery.common.container.BaseContainer;
import spinnery.common.inventory.BaseInventory;
import spinnery.common.utility.InventoryUtilities;
import spinnery.widget.WAbstractWidget;
import spinnery.widget.WInterface;
import spinnery.widget.WSlot;
import spinnery.widget.api.Action;

public class FireworkLauncherContainer extends BaseContainer {
	public static final Identifier IDENTIFIER = new Identifier(PolyDungeons.MODID, "firework_launcher");
	public static final int AMMO = 1;

	public BaseInventory inventory;

	public Text name;
	public PlayerEntity player;

	public ItemStack stack;

	public FireworkLauncherContainer(int synchronizationID, Text name, PlayerInventory playerInventory) {
		super(synchronizationID, playerInventory);
		this.name = name;
		this.player = playerInventory.player;
		stack = player.getStackInHand(player.getActiveHand());

		WInterface mainInterface = getInterface();
		if(stack.hasTag())
			inventory = InventoryUtilities.read(stack.getTag());
		else
			inventory = new BaseInventory(27);

		getInventories().put(AMMO, inventory);

		WSlot.addHeadlessArray(mainInterface, 0, AMMO, 9, 3);
		WSlot.addHeadlessPlayerInventory(mainInterface);

		inventory.addListener(sender -> stack.setTag(InventoryUtilities.write(getInventory(AMMO), new CompoundTag())));
	}

	@Override
	public void close(PlayerEntity player) {
		stack.setTag(InventoryUtilities.write(getInventory(AMMO), new CompoundTag()));

		super.close(player);
	}

	@Override
	public ItemStack transferSlot(PlayerEntity player, int index) {
		return super.transferSlot(player, index);
	}

	@Override
	public void onSlotAction(int slotNumber, int inventoryNumber, int button, Action action, PlayerEntity player) {
		System.out.println("GAMING");
		switch(action) {
			case PICKUP:
				if(PolyDungeonsItemTags.FIREWORK_LAUNCHER_AMMO.contains(getInventory(inventoryNumber).getStack(slotNumber).getItem()))
					super.onSlotAction(slotNumber,inventoryNumber,button,action,player);
				break;
		}
	}
}
