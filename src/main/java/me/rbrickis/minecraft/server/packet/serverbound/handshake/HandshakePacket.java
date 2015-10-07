package me.rbrickis.minecraft.server.packet.serverbound.handshake;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;
import me.rbrickis.minecraft.server.netty.BufferUtils;
import me.rbrickis.minecraft.server.packet.Direction;
import me.rbrickis.minecraft.server.packet.Packet;
import me.rbrickis.minecraft.server.packet.PacketInfo;
import me.rbrickis.minecraft.server.packet.State;


@Getter
@Setter
@PacketInfo(
    info = "http://wiki.vg/Protocol#Handshake",
    direction = Direction.SERVERBOUND,
    state = State.HANDSHAKE,
    id = 0x00)
public class HandshakePacket extends Packet {

    private int protocol;
    private String address;
    private int port;
    private State nextState;

    @Override
    public void decode(ByteBuf buf) {
        this.protocol = BufferUtils.readVarInt(buf);
        this.address = BufferUtils.readString(buf);
        this.port = buf.readUnsignedShort();
        this.nextState = State.fromInteger(BufferUtils.readVarInt(buf));
    }

    @Override
    public void encode(ByteBuf buf) {
        // Never being sent
    }
}
