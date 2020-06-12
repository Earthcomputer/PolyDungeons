package polydungeons.client.network;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.registry.Registry;
import polydungeons.entity.PolyDungeonsEntities;
import polydungeons.entity.SlingshotProjectileEntity;
import polydungeons.network.PolyDungeonsPackets;

import java.util.UUID;

@Environment(EnvType.CLIENT)
public class PolyDungeonsClientNetwork {
    public static void registerPackets() {
        ClientSidePacketRegistry.INSTANCE.register(PolyDungeonsPackets.CLIENTBOUND_SPAWN_ENTITY, PolyDungeonsClientNetwork::onSpawnEntity);
    }

    private static void onSpawnEntity(PacketContext context, PacketByteBuf buf) {
        // normal data
        int entityId = buf.readVarInt();
        UUID uuid = buf.readUuid();
        EntityType<?> entityType = Registry.ENTITY_TYPE.get(buf.readVarInt());
        double x = buf.readDouble();
        double y = buf.readDouble();
        double z = buf.readDouble();
        float pitch = (float) (buf.readByte() * 360) / 256;
        float yaw = (float) (buf.readByte() * 360) / 256;
        double velocityX = (double) buf.readShort() / 8000;
        double velocityY = (double) buf.readShort() / 8000;
        double velocityZ = (double) buf.readShort() / 8000;

        // custom data
        int ownerId;
        if (entityType == PolyDungeonsEntities.SLINGSHOT_PROJECTILE) {
            ownerId = buf.readVarInt();
        } else {
            ownerId = 0;
        }

        context.getTaskQueue().execute(() -> {
            ClientWorld world = (ClientWorld) context.getPlayer().world;

            Entity entity = entityType.create(world);
            if (entity != null) {
                // custom data
                if (entityType == PolyDungeonsEntities.SLINGSHOT_PROJECTILE) {
                    Entity owner = world.getEntityById(ownerId);
                    if (owner != null) {
                        ((SlingshotProjectileEntity) entity).setOwner(owner);
                    }
                }

                // normal data
                entity.updatePosition(x, y, z);
                entity.prevX = x;
                entity.prevY = y;
                entity.prevZ = z;
                entity.updateTrackedPosition(x, y, z);
                entity.pitch = pitch;
                entity.yaw = yaw;
                entity.setEntityId(entityId);
                entity.setUuid(uuid);
                entity.setVelocity(velocityX, velocityY, velocityZ);

                // spawn entity
                world.addEntity(entityId, entity);
            }
        });
    }
}
