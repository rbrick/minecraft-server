package me.rbrickis.minecraft.server.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import me.rbrickis.minecraft.server.impl.MinecraftServer;
import me.rbrickis.minecraft.server.packet.*;
import me.rbrickis.minecraft.server.session.Session;

import java.util.List;

public class MinecraftDecoder extends ByteToMessageDecoder {

    private MinecraftServer server;

    public MinecraftDecoder(MinecraftServer server) {
        this.server = server;
    }

    /**
     * This would be Serverbound packets (Packets we are receiving)
     */
    @Override
    public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() > 0) {
            Session session = server.getSessionMap().get(ctx.channel());

            if (session == null) {
                throw new RuntimeException("Session is null??!??!?!");
            }

            int id = BufferUtils.readVarInt(in); // Read the packet id
            State currentState = session.getCurrentState(); // Get the current state of the player

            PacketMap map = PacketRegistry.fromState(Direction.SERVERBOUND, currentState);

            if (map != null) {
                PacketMap.PacketEntry entry = map.fromId(id);
                if (entry != null) {
                    Class<? extends Packet> packetClazz = entry.getPacket();
                    System.out.println(packetClazz);
                    Packet packet = packetClazz.newInstance();
                    packet.decode(in); // Decode the information
                    packet.setSession(session); // Set the session!
                    out.add(packet);  // and thus we have decoded thy packet
                }
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.err.println("[ERR] " + cause.getMessage());
    }
}
