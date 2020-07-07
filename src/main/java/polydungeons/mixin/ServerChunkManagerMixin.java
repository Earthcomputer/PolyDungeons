package polydungeons.mixin;

import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import polydungeons.PolyDungeons;

@Mixin(ServerChunkManager.class)
public class ServerChunkManagerMixin {
    @Shadow @Final private ServerWorld world;

    @ModifyVariable(method = "tickChunks", ordinal = 1, at = @At(value = "STORE", ordinal = 0))
    private boolean correctDoMobSpawning(boolean doMobSpawning) {
        return doMobSpawning || (world.getRegistryKey() == World.NETHER && PolyDungeons.ignoreDoMobSpawning);
    }
}
