package me.rbrickis.minecraft.server.packet.clientbound.play;

import io.netty.buffer.ByteBuf;
import lombok.Setter;
import me.rbrickis.minecraft.server.netty.BufferUtils;
import me.rbrickis.minecraft.server.packet.ClientboundPacket;
import me.rbrickis.minecraft.server.packet.Direction;
import me.rbrickis.minecraft.server.packet.PacketInfo;
import me.rbrickis.minecraft.server.packet.State;


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

