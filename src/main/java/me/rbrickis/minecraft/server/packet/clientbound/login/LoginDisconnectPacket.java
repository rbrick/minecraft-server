package me.rbrickis.minecraft.server.packet.clientbound.login;

import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import me.rbrickis.minecraft.server.netty.BufferUtils;
import me.rbrickis.minecraft.server.packet.Direction;
import me.rbrickis.minecraft.server.packet.Packet;
import me.rbrickis.minecraft.server.packet.PacketInfo;
import me.rbrickis.minecraft.server.packet.State;

@PacketInfo(
    info = "http://wiki.vg/Protocol#Disconnect_2",
    id = 0x00,
    direction = Direction.CLIENTBOUND,
    state = State.LOGIN)
public class LoginDisconnectPacket extends Packet {

    private JsonObject reason;


    @Override
    public void decode(ByteBuf buf) {

    }

    @Override
    public void encode(ByteBuf buf) {
        BufferUtils.writeString(buf, reason.toString());
    }

    public void setReason(JsonObject reason) {
        this.reason = reason;
    }
}
