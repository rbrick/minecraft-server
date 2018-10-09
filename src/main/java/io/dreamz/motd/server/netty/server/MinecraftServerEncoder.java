package io.dreamz.motd.server.netty.server;

import io.dreamz.motd.server.connection.PlayerConnection;
import io.dreamz.motd.server.netty.BufferUtils;
import io.dreamz.motd.server.packet.*;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MinecraftServerEncoder extends MessageToByteEncoder<Packet> {


    /**
     * When writing to a channel, it will pass the object to this encoder, thus, this is how we
     * pass in Packet objects. The data will be written to the {@code out} {@link ByteBuf}
     */
    @Override
    public void encode(ChannelHandlerContext ctx, Packet in, ByteBuf out) throws Exception {
        PlayerConnection playerConnection = in.getPlayerConnection();
        State state = playerConnection.getCurrentState();
        PacketMap map = PacketRegistry.fromState(Direction.CLIENTBOUND, state);

        if (map != null) {
            int id = map.getId(in.getClass());
            BufferUtils.writeVarInt(out, id); // Write the ID
            in.encode(out);                   // encode the packet
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.err.println("[ERR] " + cause.getMessage());
    }
}
