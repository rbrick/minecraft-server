package io.dreamz.motd.server.packet.clientbound.status;

import com.google.gson.JsonObject;
import io.dreamz.motd.server.netty.BufferUtils;
import io.dreamz.motd.server.packet.ClientboundPacket;
import io.dreamz.motd.server.packet.Direction;
import io.dreamz.motd.server.packet.PacketInfo;
import io.dreamz.motd.server.packet.State;
import io.netty.buffer.ByteBuf;
import lombok.Setter;
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
