package me.rbrickis.minecraft.server.packet.clientbound.status;

import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import lombok.Setter;
import me.rbrickis.minecraft.server.netty.BufferUtils;
import me.rbrickis.minecraft.server.packet.*;

@PacketInfo(
    direction = Direction.CLIENTBOUND,
    state = State.STATUS,
    id = 0x00,
    info = "http://wiki.vg/Protocol#Response")
public class StatusResponsePacket extends ClientboundPacket {

    @Setter private JsonObject status;


    @Override
    public void encode(ByteBuf buf) {
        BufferUtils.writeString(buf, status.toString());
    }

}
