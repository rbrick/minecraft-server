package io.dreamz.motd.server.packet.clientbound.login;

import com.google.gson.JsonObject;
import io.dreamz.motd.server.netty.BufferUtils;
import io.dreamz.motd.server.packet.ClientboundPacket;
import io.dreamz.motd.server.packet.Direction;
import io.dreamz.motd.server.packet.PacketInfo;
import io.dreamz.motd.server.packet.State;
import io.netty.buffer.ByteBuf;

@PacketInfo(
    info = "http://wiki.vg/Protocol#Disconnect_2",
    id = 0x00,
    direction = Direction.CLIENTBOUND,
    state = State.LOGIN)
public class LoginDisconnectPacket extends ClientboundPacket {

    private JsonObject reason;

    @Override
    public void encode(ByteBuf buf) {
        BufferUtils.writeString(buf, reason.toString());
    }

    public void setReason(JsonObject reason) {
        this.reason = reason;
    }
}
