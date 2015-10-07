package me.rbrickis.minecraft.server.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import me.rbrickis.minecraft.server.impl.MinecraftServer;

public class ServerInitializer extends ChannelInitializer<SocketChannel> {

    private MinecraftServer server;

    public ServerInitializer(MinecraftServer server) {
        this.server = server;
    }


    @Override
    public void initChannel(SocketChannel channel) throws Exception {
        channel.pipeline().addLast("frame", new FrameHandler())
            .addLast("packet_decoder", new MinecraftDecoder(server))
            .addLast("packet_encoder", new MinecraftEncoder())
            .addLast("channel_handler", new SessionChannelHandler(server));
    }
}
