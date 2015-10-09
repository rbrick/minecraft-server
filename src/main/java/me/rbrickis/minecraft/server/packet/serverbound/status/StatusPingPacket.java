package me.rbrickis.minecraft.server.packet.serverbound.status;

import io.netty.buffer.ByteBuf;
import me.rbrickis.minecraft.server.packet.*;

@PacketInfo(
    direction = Direction.SERVERBOUND,
    info = "http://wiki.vg/Protocol#Ping",
    id = 0x01,
    state = State.STATUS)
public class StatusPingPacket extends ServerboundPacket {

    private long payload;

    @Override
    public void decode(ByteBuf buf) {
        this.payload = buf.readLong();
    }


    public long getPayload() {
        return payload;
    }
}
