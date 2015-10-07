package me.rbrickis.minecraft.server.packet.serverbound.login;

import io.netty.buffer.ByteBuf;
import me.rbrickis.minecraft.server.netty.BufferUtils;
import me.rbrickis.minecraft.server.packet.Direction;
import me.rbrickis.minecraft.server.packet.Packet;
import me.rbrickis.minecraft.server.packet.PacketInfo;
import me.rbrickis.minecraft.server.packet.State;

@PacketInfo(
    info = "http://wiki.vg/Protocol#Login_Start",
    direction = Direction.SERVERBOUND,
    state = State.LOGIN,
    id = 0x00)
public class LoginStartPacket extends Packet {

    private String name;

    @Override
    public void decode(ByteBuf buf) {
        this.name = BufferUtils.readString(buf);
    }

    @Override
    public void encode(ByteBuf buf) {

    }
}
