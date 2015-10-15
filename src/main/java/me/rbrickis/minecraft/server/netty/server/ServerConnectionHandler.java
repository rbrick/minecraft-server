package me.rbrickis.minecraft.server.netty.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import me.rbrickis.minecraft.server.impl.MinecraftServer;
import me.rbrickis.minecraft.server.packet.Packet;
import me.rbrickis.minecraft.server.packet.State;
import me.rbrickis.minecraft.server.connection.PlayerConnection;

import java.util.Optional;

public class ServerConnectionHandler extends ChannelHandlerAdapter {

    private Optional<PlayerConnection> connection = Optional.empty();

    private MinecraftServer server;

    public ServerConnectionHandler(MinecraftServer server) {
        this.server = server;
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        if (!connection.isPresent()) {
            PlayerConnection playerConnection = createConnection(ctx.channel());
            playerConnection.setCurrentState(State.HANDSHAKE);
            server.getSessionMap().put(ctx.channel(), playerConnection);
            this.connection = Optional.of(playerConnection);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().close();
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof Packet) {
            Packet packet = (Packet) msg;
            server.getEventBus().post(packet);
        }
    }


    private PlayerConnection createConnection(Channel channel) {
        if (connection.isPresent())
            return connection.get();
        return (connection = Optional.of(new PlayerConnection(channel))).get();
    }

}
