package polydungeons.loot;

import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.loot.BinomialLootTableRange;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.util.Identifier;
import polydungeons.item.PolyDungeonsItems;

public class PolyDungeonsLootTables {
	// Adding to Minecraft ones
	private static final Identifier SLIME_ENTITY = new Identifier("minecraft", "entities/slime");

	public static void registerAll() {
		LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, id, supplier, setter) -> {
			/*if (SLIME_ENTITY.equals(id)) {
				FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
						.rolls(BinomialLootTableRange.create(1, 0.025f))
						.withEntry(ItemEntry.builder(PolyDungeonsItems.SLIMY_ESSENCE).build());

				supplier.pool(poolBuilder);
			}*/
		});
	}
}
