package polydungeons.entity.charms;

import net.minecraft.entity.Entity;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class CharmHelper {

    public static <T extends Entity> T getClosestCharm(ServerWorld world, Map<UUID, Vec3d> charmPositions, Entity user, Class<T> charmType) {
        return getClosestCharm(world, charmPositions, user, charmType, charm -> true);
    }

    public static <T extends Entity> T getClosestCharm(ServerWorld world, Map<UUID, Vec3d> charmPositions, Entity user, Class<T> charmType, Predicate<? super T> filter) {
        return getCharm(world, charmPositions, user, charmType, Comparator.comparingDouble(user::squaredDistanceTo), filter);
    }

    public static <T extends Entity> T getArbitraryCharm(ServerWorld world, Map<UUID, Vec3d> charmPositions, Entity user, Class<T> charmType) {
        return getArbitraryCharm(world, charmPositions, user, charmType, charm -> true);
    }

    public static <T extends Entity> T getArbitraryCharm(ServerWorld world, Map<UUID, Vec3d> charmPositions, Entity user, Class<T> charmType, Predicate<? super T> filter) {
        return getCharm(world, charmPositions, user, charmType, null, filter);
    }

    public static <T extends Entity> T getCharm(ServerWorld world, Map<UUID, Vec3d> charmPositions, Entity user, Class<T> charmType, Comparator<Vec3d> sorter, Predicate<? super T> filter) {
        List<UUID> charmsToRemove = new ArrayList<>(0);
        Stream<Map.Entry<UUID, Vec3d>> charmStream = charmPositions.entrySet().stream();
        if (sorter != null) {
            charmStream = charmStream.sorted((a, b) -> sorter.compare(a.getValue(), b.getValue()));
        }

        T foundCharm = null;
        for (Map.Entry<UUID, Vec3d> entry : (Iterable<Map.Entry<UUID, Vec3d>>) charmStream::iterator) {
            Vec3d charmPos = entry.getValue();
            ChunkPos chunkPos = new ChunkPos(MathHelper.floor(charmPos.x / 16), MathHelper.floor(charmPos.z / 16));
            world.getChunkManager().addTicket(ChunkTicketType.field_19347, chunkPos, 1, user.getEntityId());
            world.getChunk(chunkPos.x, chunkPos.z);
            Entity entity = world.getEntity(entry.getKey());
            if (charmType.isInstance(entity)) {
                T charmEntity = charmType.cast(entity);
                if (filter.test(charmEntity)) {
                    foundCharm = charmEntity;
                    break;
                }
            } else {
                charmsToRemove.add(entry.getKey());
            }
        }

        charmsToRemove.forEach(charmPositions::remove);

        return foundCharm;
    }

}
