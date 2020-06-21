package polydungeons.client.ber.relic;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;
import polydungeons.PolyDungeons;

@Environment(EnvType.CLIENT)
public class RelicRenderParts {
    public static final ModelIdentifier RELIC_BASE = new ModelIdentifier(new Identifier(PolyDungeons.MODID, "relic/relic_base"), "");
    public static final ModelIdentifier SAMPLER = new ModelIdentifier(new Identifier(PolyDungeons.MODID, "relic/sampler"), "");
    public static final ModelIdentifier LAVA = new ModelIdentifier(new Identifier(PolyDungeons.MODID, "relic/lava"), "");
}
