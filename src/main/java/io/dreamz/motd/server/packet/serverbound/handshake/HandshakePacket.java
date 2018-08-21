package io.dreamz.motd.server.packet.serverbound.handshake;

import io.dreamz.motd.server.netty.BufferUtils;
import io.dreamz.motd.server.packet.Direction;
import io.dreamz.motd.server.packet.PacketInfo;
import io.dreamz.motd.server.packet.ServerboundPacket;
import io.dreamz.motd.server.packet.State;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@PacketInfo(
    info = "http://wiki.vg/Protocol#Handshake",
    direction = Direction.SERVERBOUND,
    state = State.HANDSHAKE,
    id = 0x00)
public class HandshakePacket extends ServerboundPacket {

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

}
