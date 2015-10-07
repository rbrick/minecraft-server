package me.rbrickis.minecraft.server.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import me.rbrickis.minecraft.server.packet.*;
import me.rbrickis.minecraft.server.session.Session;

public class MinecraftEncoder extends MessageToByteEncoder<Packet> {


    /**
     * When writing to a channel, it will pass the object to this encoder, thus, this is how we
     * pass in Packet objects. The data will be written to the {@code out} {@link ByteBuf}
     */
    @Override
    public void encode(ChannelHandlerContext ctx, Packet in, ByteBuf out) throws Exception {
        Session session = in.getSession();
        State state = session.getCurrentState();
        System.out.println("Encoder Current State: " + state);
        PacketMap map = PacketRegistry.fromState(Direction.CLIENTBOUND, state);

        if (map != null) {
            int id = map.getId(in.getClass());
            System.out.println(id);
            BufferUtils.writeVarInt(out, id); // Write the ID
            in.encode(out);                   // encode the packet
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.err.println("[ERR] " + cause.getMessage());
    }
}
