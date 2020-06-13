package polydungeons.client.network;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.registry.Registry;
import polydungeons.entity.PolyDungeonsEntities;
import polydungeons.entity.SplatEntity;
import polydungeons.network.PolyDungeonsPackets;

import java.util.UUID;

@Environment(EnvType.CLIENT)
public class PolyDungeonsClientNetwork {
    public static void registerPackets() {
        ClientSidePacketRegistry.INSTANCE.register(PolyDungeonsPackets.CLIENTBOUND_SPAWN_ENTITY, PolyDungeonsClientNetwork::onSpawnEntity);
        ClientSidePacketRegistry.INSTANCE.register(PolyDungeonsPackets.CLIENTBOUND_SPLAT_REMAINING_TICKS, PolyDungeonsClientNetwork::onSplatRemainingTicks);
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
        } else if (entityType == PolyDungeonsEntities.SPEAR) {
            ownerId = buf.readVarInt();
        } else {
            ownerId = 0;
        }
        int remainingTicks;
        if (entityType == PolyDungeonsEntities.SPLAT) {
            remainingTicks = buf.readVarInt();
        } else {
            remainingTicks = 0;
        }

        context.getTaskQueue().execute(() -> {
            ClientWorld world = (ClientWorld) context.getPlayer().world;

            Entity entity = entityType.create(world);
            if (entity != null) {
                // custom data
                if (entityType == PolyDungeonsEntities.SLINGSHOT_PROJECTILE || entityType == PolyDungeonsEntities.SPEAR) {
                    Entity owner = world.getEntityById(ownerId);
                    if (owner != null) {
                        ((PersistentProjectileEntity) entity).setOwner(owner);
                    }
                }
                if (entityType == PolyDungeonsEntities.SPLAT) {
                    ((SplatEntity) entity).setRemainingTicks(remainingTicks);
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

    private static void onSplatRemainingTicks(PacketContext context, PacketByteBuf buf) {
        int entityId = buf.readVarInt();
        int remainingTicks = buf.readVarInt();
        context.getTaskQueue().execute(() -> {
            Entity entity = context.getPlayer().world.getEntityById(entityId);
            if (entity instanceof SplatEntity) {
                ((SplatEntity) entity).setRemainingTicks(remainingTicks);
            }
        });
    }
}
