package me.rbrickis.minecraft.server.packet;

import io.netty.buffer.ByteBuf;

public abstract class ClientboundPacket extends Packet {

    @Override
    public void decode(ByteBuf buf) {
    }
}
