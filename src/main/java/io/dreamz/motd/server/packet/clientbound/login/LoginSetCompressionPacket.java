package io.dreamz.motd.server.packet.clientbound.login;

import io.dreamz.motd.server.netty.BufferUtils;
import io.dreamz.motd.server.packet.ClientboundPacket;
import io.dreamz.motd.server.packet.Direction;
import io.dreamz.motd.server.packet.PacketInfo;
import io.dreamz.motd.server.packet.State;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;


@PacketInfo(
    info = "http://wiki.vg/Protocol#Set_Compression_2",
    id = 0x03,
    direction = Direction.CLIENTBOUND,
    state = State.LOGIN)
public class LoginSetCompressionPacket extends ClientboundPacket {

    @Getter @Setter private int threshhold;

    @Override
    public void encode(ByteBuf buf) {
        BufferUtils.writeVarInt(buf, threshhold);
    }
}
