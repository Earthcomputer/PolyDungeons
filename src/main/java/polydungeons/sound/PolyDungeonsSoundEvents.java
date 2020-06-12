package polydungeons.sound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import polydungeons.PolyDungeons;

public class PolyDungeonsSoundEvents {
	public static SoundEvent SHOOT = new SoundEvent(new Identifier(PolyDungeons.MODID, "shoot"));

	public static void registerAll() {
		Registry.register(
				Registry.SOUND_EVENT,
				SHOOT.getId(),
				SHOOT
		);
	}
}
