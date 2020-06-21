package polydungeons.entity;

import net.minecraft.util.math.Vec3d;

import java.util.Map;
import java.util.UUID;

public interface IServerWorld {
    Map<UUID, Vec3d> polydungeons_getAnchorPositions();
    Map<UUID, Vec3d> polydungeons_getSubstitutePositions();
}
