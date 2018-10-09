package io.dreamz.motd.server.netty.server;

import io.dreamz.motd.server.connection.PlayerConnection;
import io.dreamz.motd.server.impl.MinecraftServer;
import io.dreamz.motd.server.packet.Packet;
import io.dreamz.motd.server.packet.State;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Optional;

public class ServerConnectionHandler extends SimpleChannelInboundHandler<Object> {

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
        server.getSessionMap().remove(ctx.channel());
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof Packet) {
            Packet packet = (Packet) msg;
            server.getEventBus().post(packet);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof Packet) {
            Packet packet = (Packet) msg;
            server.getEventBus().post(packet);
        }
    }


    private PlayerConnection createConnection(Channel channel) {
        return connection.orElseGet(() -> (connection = Optional.of(new PlayerConnection(channel))).get());
    }

}
