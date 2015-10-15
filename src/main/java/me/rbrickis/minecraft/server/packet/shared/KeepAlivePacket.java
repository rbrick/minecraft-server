package me.rbrickis.minecraft.server.packet.shared;

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
