package io.dreamz.motd.server.packet;

import io.dreamz.motd.server.connection.PlayerConnection;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;

public abstract class Packet {

    @Getter @Setter private PlayerConnection playerConnection;

    public abstract void decode(ByteBuf buf);

    public abstract void encode(ByteBuf buf);
}
