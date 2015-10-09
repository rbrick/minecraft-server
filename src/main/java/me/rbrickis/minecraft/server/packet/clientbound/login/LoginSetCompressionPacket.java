package me.rbrickis.minecraft.server.packet.clientbound.login;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;
import me.rbrickis.minecraft.server.netty.BufferUtils;
import me.rbrickis.minecraft.server.packet.*;


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
