package me.rbrickis.minecraft.server.packet;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;
import me.rbrickis.minecraft.server.connection.PlayerConnection;

public abstract class Packet {

    @Getter @Setter private PlayerConnection playerConnection;

    public abstract void decode(ByteBuf buf);

    public abstract void encode(ByteBuf buf);
}
