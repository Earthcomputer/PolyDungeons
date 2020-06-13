package polydungeons.network;

import net.minecraft.util.Identifier;
import polydungeons.PolyDungeons;

public class PolyDungeonsPackets {
    public static final Identifier CLIENTBOUND_SPAWN_ENTITY = new Identifier(PolyDungeons.MODID, "spawn_entity");
    public static final Identifier CLIENTBOUND_SPLAT_REMAINING_TICKS = new Identifier(PolyDungeons.MODID, "splat_remaining_ticks");
}
