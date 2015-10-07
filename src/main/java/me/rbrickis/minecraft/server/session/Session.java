package me.rbrickis.minecraft.server.session;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import lombok.Getter;
import lombok.Setter;
import me.rbrickis.minecraft.server.packet.Packet;
import me.rbrickis.minecraft.server.packet.State;

public class Session {

    @Getter private Channel channel;

    @Getter @Setter private State currentState;

    public Session(Channel channel) {
        this.channel = channel;
    }

    public ChannelFuture sendPacket(Packet packet) {
        packet.setSession(this);
        return channel.writeAndFlush(packet);
    }

}
