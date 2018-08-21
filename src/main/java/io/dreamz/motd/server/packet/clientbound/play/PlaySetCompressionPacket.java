package io.dreamz.motd.server.packet.clientbound.play;

import io.dreamz.motd.server.netty.BufferUtils;
import io.dreamz.motd.server.packet.ClientboundPacket;
import io.dreamz.motd.server.packet.Direction;
import io.dreamz.motd.server.packet.PacketInfo;
import io.dreamz.motd.server.packet.State;
import io.netty.buffer.ByteBuf;
import lombok.Setter;


@PacketInfo(
    direction = Direction.CLIENTBOUND,
    id = 0x46,
    info = "http://wiki.vg/Protocol#Set_Compression",
    state = State.PLAY)
public class PlaySetCompressionPacket extends ClientboundPacket {

    @Setter private int threshhold;

    @Override
    public void encode(ByteBuf buf) {
        BufferUtils.writeVarInt(buf, threshhold);
    }
}

