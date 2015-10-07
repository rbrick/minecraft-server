package me.rbrickis.minecraft.server.packet.serverbound.status;

import io.netty.buffer.ByteBuf;
import me.rbrickis.minecraft.server.packet.Direction;
import me.rbrickis.minecraft.server.packet.Packet;
import me.rbrickis.minecraft.server.packet.PacketInfo;
import me.rbrickis.minecraft.server.packet.State;

@PacketInfo(
    direction = Direction.SERVERBOUND,
    id = 0x00,
    info = "http://wiki.vg/Protocol#Request",
    state = State.STATUS)
public class StatusRequestPacket extends Packet {
    @Override
    public void decode(ByteBuf buf) {
        System.out.println("Decoding for StatusRequest");
    }

    @Override
    public void encode(ByteBuf buf) {
    }
}
