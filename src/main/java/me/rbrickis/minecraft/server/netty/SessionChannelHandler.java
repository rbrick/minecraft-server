package me.rbrickis.minecraft.server.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import me.rbrickis.minecraft.server.impl.MinecraftServer;
import me.rbrickis.minecraft.server.packet.Packet;
import me.rbrickis.minecraft.server.packet.State;
import me.rbrickis.minecraft.server.session.Session;

import java.util.Optional;

public class SessionChannelHandler extends ChannelHandlerAdapter {

    private Optional<Session> session = Optional.<Session>empty();


    private MinecraftServer server;

    public SessionChannelHandler(MinecraftServer server) {
        this.server = server;
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        if (!session.isPresent()) {
            Session session = createSession(ctx.channel());
            session.setCurrentState(State.HANDSHAKE);
            server.getSessionMap().put(ctx.channel(), session);
            this.session = Optional.of(session);
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
            System.out.println(packet.getClass().getSimpleName());
            server.getEventBus().post(packet);
        }
    }


    private Session createSession(Channel channel) {
        if (session.isPresent())
            return session.get();
        return (session = Optional.of(new Session(channel))).get();
    }

}
