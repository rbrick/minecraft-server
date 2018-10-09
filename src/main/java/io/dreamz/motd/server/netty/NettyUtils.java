package io.dreamz.motd.server.netty;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public final class NettyUtils {

    public static EventLoopGroup getEpollLoopGroupIfExist() {
        return Epoll.isAvailable() ?
                new EpollEventLoopGroup() : new NioEventLoopGroup();
    }

    public static Class<? extends ServerSocketChannel> getEpollServerSocketChannelIfExist() {
        return Epoll.isAvailable() ?
                EpollServerSocketChannel.class : NioServerSocketChannel.class;
    }

}
