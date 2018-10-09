package io.dreamz.motd.server.packet.clientbound.status;

import io.dreamz.motd.server.packet.ClientboundPacket;
import io.dreamz.motd.server.packet.Direction;
import io.dreamz.motd.server.packet.PacketInfo;
import io.dreamz.motd.server.packet.State;
import io.netty.buffer.ByteBuf;
import lombok.Setter;



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
