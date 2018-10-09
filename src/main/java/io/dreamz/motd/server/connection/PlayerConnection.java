package io.dreamz.motd.server.connection;

import io.dreamz.motd.server.packet.Packet;
import io.dreamz.motd.server.packet.State;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class PlayerConnection {

    @Getter private Channel channel;

    @Getter @Setter private State currentState;

    @Getter @Setter private int protocol = -1;

    @Getter @Setter private String name;
    @Getter @Setter private UUID uuid;


    public PlayerConnection(Channel channel) {
        this.channel = channel;
    }

    public ChannelFuture sendPacket(Packet packet) {
        packet.setPlayerConnection(this);
        return channel.writeAndFlush(packet);
    }
}
