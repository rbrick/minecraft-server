package me.rbrickis.minecraft.server.packet.clientbound.status;

import io.netty.buffer.ByteBuf;
import lombok.Setter;
import me.rbrickis.minecraft.server.packet.*;


@PacketInfo(
    direction = Direction.CLIENTBOUND,
    state = State.STATUS,
    id = 0x01,
    info = "http://wiki.vg/Protocol#Pong")
public class StatusPongPacket extends ClientboundPacket {

    @Setter private long payload;

    @Override
    public void encode(ByteBuf buf) {
        buf.writeLong(payload);
    }

}
