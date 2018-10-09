package io.dreamz.motd.server.netty.server;

import io.dreamz.motd.server.impl.MinecraftServer;
import io.dreamz.motd.server.netty.FrameHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class ServerInitializer extends ChannelInitializer<SocketChannel> {

    private MinecraftServer server;

    public ServerInitializer(MinecraftServer server) {
        this.server = server;
    }


    @Override
    public void initChannel(SocketChannel channel) throws Exception {
        channel.pipeline().addLast("frame", new FrameHandler())
            .addLast("packet_decoder", new MinecraftServerDecoder(server))
            .addLast("packet_encoder", new MinecraftServerEncoder())
            .addLast("channel_handler", new ServerConnectionHandler(server));
    }
}
