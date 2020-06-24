package polydungeons.mixin;

import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.WorldSavePath;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import polydungeons.PolyDungeons;
import polydungeons.entity.IServerWorld;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin extends World implements IServerWorld {
    @Shadow @Final private MinecraftServer server;

    @Unique
    private Map<UUID, Vec3d> anchorPositions = new HashMap<>();
    @Unique
    private Map<UUID, Vec3d> substitutePositions = new HashMap<>();

    protected ServerWorldMixin(MutableWorldProperties mutableWorldProperties, RegistryKey<World> registryKey, RegistryKey<DimensionType> registryKey2, DimensionType dimensionType, Supplier<Profiler> profiler, boolean bl, boolean bl2, long l) {
        super(mutableWorldProperties, registryKey, registryKey2, dimensionType, profiler, bl, bl2, l);
    }

    @Override
    public Map<UUID, Vec3d> polydungeons_getAnchorPositions() {
        return anchorPositions;
    }

    @Override
    public Map<UUID, Vec3d> polydungeons_getSubstitutePositions() {
        return substitutePositions;
    }

    @Unique
    private void readCharms(CompoundTag rootTag) {
        // Add more charms here

        ListTag anchorPosList = rootTag.getList("Anchors", NbtType.COMPOUND);
        readEntitiesOfInterest(anchorPosList, anchorPositions);

        ListTag substitutePosList = rootTag.getList("Substitutes", NbtType.COMPOUND);
        readEntitiesOfInterest(substitutePosList, substitutePositions);
    }

    @Unique
    private void writeCharms(CompoundTag rootTag) {
        // Add more charms here
        rootTag.put("Anchors", writeEntitiesOfInterest(anchorPositions));
        rootTag.put("Substitutes", writeEntitiesOfInterest(substitutePositions));
    }




    @Inject(method = "<init>", at = @At("RETURN"))
    private void onLoadLevel(CallbackInfo ci) {
        File polydungeonsDir = new File(DimensionType.getSaveDirectory(getRegistryKey(), server.getSavePath(WorldSavePath.ROOT).toFile()), PolyDungeons.MODID);
        File anchorPositionsFile = new File(polydungeonsDir, "entities_of_interest.dat");
        if (anchorPositionsFile.isFile()) {
            try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(anchorPositionsFile))) {
                CompoundTag rootTag = NbtIo.readCompressed(in);
                readCharms(rootTag);
            } catch (IOException e) {
                LOGGER.error("Failed to read " + PolyDungeons.MODID +  " entities of interest", e);
            }
        }
    }

    @Inject(method = "saveLevel", at = @At("RETURN"))
    private void onSaveLevel(CallbackInfo ci) {
        File polydungeonsDir = new File(DimensionType.getSaveDirectory(getRegistryKey(), server.getSavePath(WorldSavePath.ROOT).toFile()), PolyDungeons.MODID);
        if (!polydungeonsDir.exists() && !polydungeonsDir.mkdirs()) {
            LOGGER.error("Failed to create " + PolyDungeons.MODID + " directory");
            return;
        }
        File anchorPositionsFile = new File(polydungeonsDir, "entities_of_interest.dat");
        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(anchorPositionsFile))) {
            CompoundTag rootTag = new CompoundTag();
            writeCharms(rootTag);
            NbtIo.writeCompressed(rootTag, out);
        } catch (IOException e) {
            LOGGER.error("Failed to write " + PolyDungeons.MODID + " entities of interest", e);
        }
    }

    @Unique
    private void readEntitiesOfInterest(ListTag interestList, Map<UUID, Vec3d> entitiesOfInterest) {
        entitiesOfInterest.clear();
        for (int i = 0; i < interestList.size(); i++) {
            CompoundTag interestPos = interestList.getCompound(i);
            UUID uuid = interestPos.getUuid("UUID");
            ListTag posList = interestPos.getList("Pos", NbtType.DOUBLE);
            if (posList.size() >= 3) {
                Vec3d pos = new Vec3d(posList.getDouble(0), posList.getDouble(1), posList.getDouble(2));
                entitiesOfInterest.put(uuid, pos);
            }
        }
    }

    @Unique
    private ListTag writeEntitiesOfInterest(Map<UUID, Vec3d> entitiesOfInterest) {
        ListTag interestList = new ListTag();
        entitiesOfInterest.forEach((uuid, pos) -> {
            CompoundTag interestPos = new CompoundTag();
            interestPos.putUuid("UUID", uuid);
            ListTag posTag = new ListTag();
            Collections.addAll(posTag, DoubleTag.of(pos.x), DoubleTag.of(pos.y), DoubleTag.of(pos.z));
            interestPos.put("Pos", posTag);
            interestList.add(interestPos);
        });
        return interestList;
    }
}
