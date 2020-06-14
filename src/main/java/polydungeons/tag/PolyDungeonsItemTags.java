package polydungeons.tag;

import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import polydungeons.PolyDungeons;

public class PolyDungeonsItemTags {
	public static final Tag<Item> FIREWORK_LAUNCHER_AMMO = TagRegistry.item(new Identifier(PolyDungeons.MODID, "firework_launcher_ammo"));
}
