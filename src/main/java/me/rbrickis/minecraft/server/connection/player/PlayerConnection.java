package me.rbrickis.minecraft.server.connection.player;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import lombok.Getter;
import lombok.Setter;
import me.rbrickis.minecraft.server.packet.Packet;
import me.rbrickis.minecraft.server.packet.State;

import java.net.InetSocketAddress;
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
        System.out.println("Sending packet: " + packet.getClass().getSimpleName());
        return channel.writeAndFlush(packet);
    }


    public void redirect(InetSocketAddress address) {
//        this.setCurrentState(State.HANDSHAKE);
//      //  channel.close();
//        channel.connect(address);

    }

    /*
       Not sure what the best method would be
     */
    public void startKeepAliveThread() {

    }


}
