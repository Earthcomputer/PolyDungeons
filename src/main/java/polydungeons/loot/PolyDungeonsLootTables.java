package polydungeons.loot;

import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.item.Items;
import net.minecraft.loot.BinomialLootTableRange;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.ExplorationMapLootFunction;
import net.minecraft.util.Identifier;
import polydungeons.structures.DungeonData;

public class PolyDungeonsLootTables {
	// Adding to Minecraft ones
	private static final Identifier FORTRESS = new Identifier("minecraft", "chests/nether_bridge");

	public static void registerAll() {
		LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, id, supplier, setter) -> {
			if(FORTRESS.equals(id)) {
				FabricLootPoolBuilder builder =
						FabricLootPoolBuilder.builder()
								.rolls(BinomialLootTableRange.create(1, 0.1f))
								.withEntry(ItemEntry.builder(Items.MAP).build())
								.withFunction(ExplorationMapLootFunction.create().withDestination(DungeonData.NETHER_DUNGEON).build());
				supplier.pool(builder);
			}
		});
	}
}
