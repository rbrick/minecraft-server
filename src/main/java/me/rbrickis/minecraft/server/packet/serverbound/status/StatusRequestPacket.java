package me.rbrickis.minecraft.server.packet.serverbound.status;

import io.netty.buffer.ByteBuf;
import me.rbrickis.minecraft.server.packet.*;

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
