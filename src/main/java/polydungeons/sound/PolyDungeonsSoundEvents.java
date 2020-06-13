package polydungeons.sound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import polydungeons.PolyDungeons;

public class PolyDungeonsSoundEvents {
	public static final SoundEvent SHOOT = register("shoot");
	public static final SoundEvent SLINGSHOT = register("slingshot");
	public static final SoundEvent SPLAT = register("splat");

	private static SoundEvent register(String id) {
		Identifier identifier = new Identifier(PolyDungeons.MODID, id);
		return Registry.register(Registry.SOUND_EVENT, identifier, new SoundEvent(identifier));
	}

	public static void registerAll() {
		// load class
	}
}
