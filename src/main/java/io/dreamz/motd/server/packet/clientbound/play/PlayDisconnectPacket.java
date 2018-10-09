package io.dreamz.motd.server.packet.clientbound.play;

import com.google.gson.JsonObject;
import io.dreamz.motd.server.netty.BufferUtils;
import io.dreamz.motd.server.packet.ClientboundPacket;
import io.dreamz.motd.server.packet.Direction;
import io.dreamz.motd.server.packet.PacketInfo;
import io.dreamz.motd.server.packet.State;
import io.netty.buffer.ByteBuf;
import lombok.Setter;

@Setter
@PacketInfo(
        id = 0x40,
        direction = Direction.CLIENTBOUND,
        info = "http://wiki.vg/Protocol#Disconnect",
        state = State.PLAY)
public class PlayDisconnectPacket extends ClientboundPacket {

    private JsonObject reason;

    @Override
    public void encode(ByteBuf buf) {
        BufferUtils.writeString(buf, reason.toString());
    }
}
