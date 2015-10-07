package me.rbrickis.minecraft.server.packet.clientbound.status;

import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import me.rbrickis.minecraft.server.netty.BufferUtils;
import me.rbrickis.minecraft.server.packet.Direction;
import me.rbrickis.minecraft.server.packet.Packet;
import me.rbrickis.minecraft.server.packet.PacketInfo;
import me.rbrickis.minecraft.server.packet.State;

@PacketInfo(
    direction = Direction.CLIENTBOUND,
    state = State.STATUS,
    id = 0x00,
    info = "http://wiki.vg/Protocol#Response")
public class StatusResponsePacket extends Packet {

    private JsonObject status;


    @Override
    public void decode(ByteBuf buf) {
    }

    @Override
    public void encode(ByteBuf buf) {
        BufferUtils.writeString(buf, status.toString());
    }


    public void setStatus(JsonObject status) {
        this.status = status;
    }
}
