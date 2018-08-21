package io.dreamz.motd.server.packet;

import io.netty.buffer.ByteBuf;

public abstract class ServerboundPacket extends Packet {

    @Override
    public void encode(ByteBuf buf) {
    }
}
