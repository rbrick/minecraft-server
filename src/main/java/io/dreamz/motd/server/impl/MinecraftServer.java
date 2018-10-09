package io.dreamz.motd.server.impl;

import com.google.common.eventbus.EventBus;
import io.dreamz.motd.server.api.Server;
import io.dreamz.motd.server.connection.PlayerConnection;
import io.dreamz.motd.server.netty.NettyUtils;
import io.dreamz.motd.server.netty.server.ServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class MinecraftServer implements Server {

    private EventLoopGroup boss, worker;
    private Map<Channel, PlayerConnection> sessionMap = new ConcurrentHashMap<>();
    private ServerBootstrap bootstrap;
    private int port;
    private EventBus eventBus = new EventBus();

    public MinecraftServer(int port) {
        this.port = port;
        this.boss = NettyUtils.getEpollLoopGroupIfExist();
        this.worker = NettyUtils.getEpollLoopGroupIfExist();
        this.bootstrap = new ServerBootstrap().group(boss, worker).channel(
                NettyUtils.getEpollServerSocketChannelIfExist())          // Type of server socket we use
                .childHandler(new ServerInitializer(this))          // our channel initializer
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true); // keep the connection alive

    }


    @Override
    public void start() {
        try {
            ChannelFuture future = this.bootstrap.bind(port).sync();
            future.channel().closeFuture().sync().addListener(future1 -> {
                System.out.println("Good bye.");
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String getName() {
        return "Generic Minecraft Server";
    }

    @Override
    public String getVersion() {
        return "0.0.1-SNAPSHOT";
    }

    @Override
    public int getProtocol() {
        return 47;
    }

    @Override
    public void shutdown() {
        worker.shutdownGracefully();
        boss.shutdownGracefully();
    }

    public Map<Channel, PlayerConnection> getSessionMap() {
        return sessionMap;
    }

    public EventBus getEventBus() {
        return eventBus;
    }
}

