package me.rbrickis.minecraft.server.packet.serverbound.status;

import io.netty.buffer.ByteBuf;
import me.rbrickis.minecraft.server.packet.Direction;
import me.rbrickis.minecraft.server.packet.Packet;
import me.rbrickis.minecraft.server.packet.PacketInfo;
import me.rbrickis.minecraft.server.packet.State;

@PacketInfo(
    direction = Direction.SERVERBOUND,
    info = "http://wiki.vg/Protocol#Ping",
    id = 0x01,
    state = State.STATUS)
public class StatusPingPacket extends Packet {

    private long payload;

    @Override
    public void decode(ByteBuf buf) {
        this.payload = buf.readLong();
    }

    @Override
    public void encode(ByteBuf buf) {
    }


    public long getPayload() {
        return payload;
    }
}
