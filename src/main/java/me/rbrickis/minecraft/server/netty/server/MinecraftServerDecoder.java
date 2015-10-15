package me.rbrickis.minecraft.server.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import me.rbrickis.minecraft.server.impl.MinecraftServer;
import me.rbrickis.minecraft.server.netty.BufferUtils;
import me.rbrickis.minecraft.server.packet.*;
import me.rbrickis.minecraft.server.connection.player.PlayerConnection;

import java.util.List;

public class MinecraftServerDecoder extends ByteToMessageDecoder {

    private MinecraftServer server;

    public MinecraftServerDecoder(MinecraftServer server) {
        this.server = server;
    }

    /**
     * This would be Serverbound packets (Packets we are receiving)
     */
    @Override
    public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() > 0) {
            PlayerConnection playerConnection = server.getSessionMap().get(ctx.channel());

            if (playerConnection == null) {
                throw new RuntimeException("Session is null??!??!?!");
            }

            int id = BufferUtils.readVarInt(in); // Read the packet id
            State currentState = playerConnection.getCurrentState(); // Get the current state of the player

            PacketMap map = PacketRegistry.fromState(Direction.SERVERBOUND, currentState);

            if (map != null) {
                PacketMap.PacketEntry entry = map.fromId(id);
                if (entry != null) {
                    Class<? extends Packet> packetClazz = entry.getPacket();
                    Packet packet = packetClazz.newInstance();
                    packet.decode(in); // Decode the information
                    packet.setPlayerConnection(playerConnection); // Set the session!
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
