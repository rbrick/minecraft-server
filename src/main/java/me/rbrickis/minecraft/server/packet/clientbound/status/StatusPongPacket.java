package me.rbrickis.minecraft.server.packet.clientbound.status;

import io.netty.buffer.ByteBuf;
import me.rbrickis.minecraft.server.packet.Direction;
import me.rbrickis.minecraft.server.packet.Packet;
import me.rbrickis.minecraft.server.packet.PacketInfo;
import me.rbrickis.minecraft.server.packet.State;


@PacketInfo(
    direction = Direction.CLIENTBOUND,
    state = State.STATUS,
    id = 0x01,
    info = "http://wiki.vg/Protocol#Pong")
public class StatusPongPacket extends Packet {

    private long payload;

    @Override
    public void decode(ByteBuf buf) {

    }

    @Override
    public void encode(ByteBuf buf) {
        buf.writeLong(payload);
    }

    public void setPayload(long payload) {
        this.payload = payload;
    }
}
