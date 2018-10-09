package io.dreamz.motd.server.packet.serverbound.status;

import io.dreamz.motd.server.packet.Direction;
import io.dreamz.motd.server.packet.PacketInfo;
import io.dreamz.motd.server.packet.ServerboundPacket;
import io.dreamz.motd.server.packet.State;
import io.netty.buffer.ByteBuf;


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
