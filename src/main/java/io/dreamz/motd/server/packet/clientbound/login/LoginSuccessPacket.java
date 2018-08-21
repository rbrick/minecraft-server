package io.dreamz.motd.server.packet.clientbound.login;

import io.dreamz.motd.server.netty.BufferUtils;
import io.dreamz.motd.server.packet.ClientboundPacket;
import io.dreamz.motd.server.packet.Direction;
import io.dreamz.motd.server.packet.PacketInfo;
import io.dreamz.motd.server.packet.State;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@PacketInfo(
    info = "http://wiki.vg/Protocol#Login_Success",
    id = 0x02,
    direction = Direction.CLIENTBOUND,
    state = State.LOGIN)
@Getter
@Setter
public class LoginSuccessPacket extends ClientboundPacket {

    private String name; // Players name
    private UUID id;     // Players uuid

    @Override
    public void encode(ByteBuf buf) {
        BufferUtils.writeString(buf, id.toString());
        BufferUtils.writeString(buf, name);
    }
}
