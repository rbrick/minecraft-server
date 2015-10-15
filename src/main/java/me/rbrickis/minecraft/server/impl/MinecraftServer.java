package me.rbrickis.minecraft.server.impl;

import com.google.common.eventbus.EventBus;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import me.rbrickis.minecraft.server.Main;
import me.rbrickis.minecraft.server.api.Server;
import me.rbrickis.minecraft.server.netty.server.ServerInitializer;
import me.rbrickis.minecraft.server.connection.player.PlayerConnection;

import java.util.HashMap;
import java.util.Map;


public class MinecraftServer implements Server {

    private EventLoopGroup boss, worker;
    private Map<Channel, PlayerConnection> sessionMap = new HashMap<>();
    private ServerBootstrap bootstrap;
    private int port;
    private EventBus eventBus = new EventBus();

//    public static final PublicKey PUBLIC_KEY = null;

    public MinecraftServer(int port) {
        this.port = port;
        this.boss = new NioEventLoopGroup();
        this.worker = new NioEventLoopGroup();
        this.bootstrap = new ServerBootstrap().group(boss, worker).channel(
            NioServerSocketChannel.class)          // Type of server socket we use
            .childHandler(new ServerInitializer(this))          // our channel initializer
            .childOption(ChannelOption.TCP_NODELAY, true)
            .childOption(ChannelOption.SO_KEEPALIVE, true); // keep the connection alive

    }



    @Override
    public void start() {
        try {
            ChannelFuture future = this.bootstrap.bind(port).sync();
            future.channel().closeFuture().sync().addListener(future1 -> {
                Main.TIMER.purge();
                Main.TIMER.cancel();
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

