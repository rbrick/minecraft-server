package io.dreamz.motd.server.packet.serverbound.login;

import io.dreamz.motd.server.netty.BufferUtils;
import io.dreamz.motd.server.packet.Direction;
import io.dreamz.motd.server.packet.PacketInfo;
import io.dreamz.motd.server.packet.ServerboundPacket;
import io.dreamz.motd.server.packet.State;
import io.netty.buffer.ByteBuf;


@PacketInfo(
    info = "http://wiki.vg/Protocol#Login_Start",
    direction = Direction.SERVERBOUND,
    state = State.LOGIN,
    id = 0x00)
public class LoginStartPacket extends ServerboundPacket {

    private String name;

    @Override
    public void decode(ByteBuf buf) {
        this.name = BufferUtils.readString(buf);
    }


    public String getName() {
        return name;
    }
}
