package polydungeons.network;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;

import java.util.function.Consumer;

public class PolyDungeonsServerNetwork {
    public static void registerPackets() {

    }

    public static Packet<?> spawnEntity(Entity entity) {
        return spawnEntity(entity, buf -> {});
    }

    public static Packet<?> spawnEntity(Entity entity, Consumer<PacketByteBuf> extraData) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeVarInt(entity.getEntityId());
        buf.writeUuid(entity.getUuid());
        buf.writeVarInt(Registry.ENTITY_TYPE.getRawId(entity.getType()));
        buf.writeDouble(entity.getX());
        buf.writeDouble(entity.getY());
        buf.writeDouble(entity.getZ());
        buf.writeByte(MathHelper.floor(entity.pitch * 256 / 360));
        buf.writeByte(MathHelper.floor(entity.yaw * 256 / 360));
        buf.writeShort((int) (MathHelper.clamp(entity.getVelocity().x, -3.9, 3.9) * 8000));
        buf.writeShort((int) (MathHelper.clamp(entity.getVelocity().y, -3.9, 3.9) * 8000));
        buf.writeShort((int) (MathHelper.clamp(entity.getVelocity().z, -3.9, 3.9) * 8000));
        extraData.accept(buf);
        return ServerSidePacketRegistry.INSTANCE.toPacket(PolyDungeonsPackets.CLIENTBOUND_SPAWN_ENTITY, buf);
    }

    public static Packet<?> splatRemainingTicks(Entity entity, int remainingTicks) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeVarInt(entity.getEntityId());
        buf.writeVarInt(remainingTicks);
        return ServerSidePacketRegistry.INSTANCE.toPacket(PolyDungeonsPackets.CLIENTBOUND_SPLAT_REMAINING_TICKS, buf);
    }
}
