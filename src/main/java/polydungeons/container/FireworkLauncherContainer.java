package polydungeons.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import polydungeons.PolyDungeons;
import spinnery.common.container.BaseContainer;
import spinnery.common.inventory.BaseInventory;
import spinnery.widget.WInterface;
import spinnery.widget.WSlot;

public class FireworkLauncherContainer extends BaseContainer {
	public static final Identifier IDENTIFIER = new Identifier(PolyDungeons.MODID, "firework_launcher");
	public static final int AMMO = 1;

	public BaseInventory inventory;

	public Text name;
	public PlayerEntity player;

	public ItemStack fireworkLauncher;

	public int gunpowder = 0;
	public int paper = 0;

	public FireworkLauncherContainer(int synchronizationID, Text name, PlayerInventory playerInventory) {
		super(synchronizationID, playerInventory);
//		this.name = name;
//		this.player = playerInventory.player;
//		fireworkLauncher = player.getStackInHand(player.getActiveHand());
//
//		WInterface mainInterface = getInterface();
//
//		if(fireworkLauncher.hasTag())
//			inventory = InventoryUtilities.read(fireworkLauncher.getTag());
//		else
//			inventory = new BaseInventory(1);
//
//		getInventories().put(AMMO, inventory);
//
//		WSlot slot = mainInterface.createChild(WSlot::new)
//				.setSlotNumber(0).setInventoryNumber(AMMO)
////				.setWhitelist().accept(PolyDungeonsItemTags.FIREWORK_LAUNCHER_AMMO)
//				;
//
//		mainInterface.add(slot);
//
//		WSlot.addHeadlessPlayerInventory(mainInterface);
//
//		inventory.addListener(sender -> {
////			sender.clear();
//			ItemStack stack = sender.getStack(0);
//			if (stack.getItem().equals(Items.GUNPOWDER))
//				gunpowder++;
//			else if (stack.getItem().equals(Items.PAPER))
//				paper++;
////			inventory.removeStack(0);
//			saveNBT();
//		});
		this.name = name;
		this.player = playerInventory.player;
		WInterface mainInterface = getInterface();
		BaseInventory backpackInventory = new BaseInventory(27);

		getInventories().put(1, backpackInventory);

		WSlot.addHeadlessArray(mainInterface, 0, 1, 9, 3);
		WSlot.addHeadlessPlayerInventory(mainInterface);

	}

	@Override
	public void close(PlayerEntity player) {
//		fireworkLauncher.setTag(InventoryUtilities.write(getInventory(AMMO), new CompoundTag()));
//		saveNBT();
		super.close(player);
	}
	public void saveNBT() {
//		fireworkLauncher.putSubTag("Gunpowder", IntTag.of(gunpowder));
//		fireworkLauncher.putSubTag("Paper", IntTag.of(paper));
	}
}
