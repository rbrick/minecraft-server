package io.dreamz.motd.server.packet.serverbound.status;

import io.dreamz.motd.server.packet.Direction;
import io.dreamz.motd.server.packet.PacketInfo;
import io.dreamz.motd.server.packet.ServerboundPacket;
import io.dreamz.motd.server.packet.State;
import io.netty.buffer.ByteBuf;

@PacketInfo(
    direction = Direction.SERVERBOUND,
    id = 0x00,
    info = "http://wiki.vg/Protocol#Request",
    state = State.STATUS)
public class StatusRequestPacket extends ServerboundPacket {
    @Override
    public void decode(ByteBuf buf) {
        // NO-OP
    }
}
