package io.dreamz.motd.server.packet.shared;

import io.dreamz.motd.server.netty.BufferUtils;
import io.dreamz.motd.server.packet.Direction;
import io.dreamz.motd.server.packet.Packet;
import io.dreamz.motd.server.packet.PacketInfo;
import io.dreamz.motd.server.packet.State;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@PacketInfo(
    id = 0x00,
    state = State.PLAY,
    direction = Direction.SHARED,
    info = "http://wiki.vg/Protocol#Keep_Alive")
public class KeepAlivePacket extends Packet {

    private int id;

    @Override
    public void decode(ByteBuf buf) {
        this.id = BufferUtils.readVarInt(buf);
    }

    @Override
    public void encode(ByteBuf buf) {
        BufferUtils.writeVarInt(buf, id);
    }
}
