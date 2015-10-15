package me.rbrickis.minecraft.server;

import com.google.common.escape.Escaper;
import com.google.common.escape.Escapers;
import com.google.common.eventbus.Subscribe;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import me.rbrickis.minecraft.server.connection.PlayerConnection;
import me.rbrickis.minecraft.server.packet.State;
import me.rbrickis.minecraft.server.packet.clientbound.login.LoginSuccessPacket;
import me.rbrickis.minecraft.server.packet.clientbound.play.PlayDisconnectPacket;
import me.rbrickis.minecraft.server.packet.clientbound.play.PlaySetCompressionPacket;
import me.rbrickis.minecraft.server.packet.clientbound.status.StatusPongPacket;
import me.rbrickis.minecraft.server.packet.clientbound.status.StatusResponsePacket;
import me.rbrickis.minecraft.server.packet.serverbound.handshake.HandshakePacket;
import me.rbrickis.minecraft.server.packet.serverbound.login.LoginStartPacket;
import me.rbrickis.minecraft.server.packet.serverbound.status.StatusPingPacket;
import me.rbrickis.minecraft.server.packet.serverbound.status.StatusRequestPacket;

import java.util.Random;
import java.util.UUID;

public class PacketListener {

    private Random random = new Random(System.currentTimeMillis());


    @Subscribe
    public void onHandshake(HandshakePacket handshake) {
        System.out.println("Protocol : " + handshake.getProtocol());
        System.out.println("Address  : " + handshake.getAddress());
        System.out.println("Port     : " + handshake.getPort());
        System.out.println("State    : " + handshake.getNextState());

        State state = handshake.getNextState();
        PlayerConnection playerConnection = handshake.getPlayerConnection();
        playerConnection.setCurrentState(state);
        playerConnection.setProtocol(handshake.getProtocol());
    }

    @Subscribe
    public void onStatusRequest(StatusRequestPacket request) {
        PlayerConnection playerConnection = request.getPlayerConnection();
        String motd = "§4§l420 §6§lBlaze §e§lit!";
        Escaper escaper = Escapers.builder().addEscape('§', "\u00A7").build();

        JsonObject status = new JsonObject();
        {
            JsonObject version = new JsonObject();
            {
                version.addProperty("name", escaper.escape("§r§c§lPlayers: §f9001§6/§f-1337"));
                version.addProperty("protocol", -1);
            }

            JsonObject players = new JsonObject();
            {
                players.addProperty("max", -0x7777777);
                players.addProperty("online", 9001);

                JsonArray array = new JsonArray();

                JsonObject player1 = new JsonObject();
                {
                    player1.addProperty("name", escaper.escape("§c§lHello World"));
                    player1.addProperty("id",
                        UUID.fromString("4566e69f-c907-48ee-8d71-d7ba5aa00d20").toString());
                }

                JsonObject player2 = new JsonObject();
                {
                    player2.addProperty("name", escaper
                        .escape("§6§lThis server was made by §arbrick §6§land uses §cNetty 5.0"));
                    player2.addProperty("id", UUID.randomUUID().toString());
                }


                array.add(player1);
                array.add(player2);

                players.add("sample", array);

            }

            JsonObject description = new JsonObject();
            {
                description.addProperty("text", escaper.escape(motd));
            }


            status.add("version", version);
            status.add("players", players);
            status.add("description", description);
            System.out.println(status.toString());
        }

        final StatusResponsePacket packet = new StatusResponsePacket();

        packet.setStatus(status);
        playerConnection.sendPacket(packet);
    }


    @Subscribe
    public void onStatusPing(StatusPingPacket ping) {
        PlayerConnection playerConnection = ping.getPlayerConnection();
        long payload = ping.getPayload();
        StatusPongPacket pong = new StatusPongPacket();
        pong.setPayload(payload);
        playerConnection.sendPacket(pong);
    }

    @Subscribe
    public void onLoginStart(LoginStartPacket loginStart) {
        PlayerConnection playerConnection = loginStart.getPlayerConnection();

        String name = loginStart.getName();
        UUID id = UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes());

        LoginSuccessPacket packet = new LoginSuccessPacket();
        packet.setId(id);
        packet.setName(name);

        playerConnection.sendPacket(packet); // Send the packet

        // Now that packet is sent, set the state to play
        playerConnection.setCurrentState(State.PLAY);  // Set the play state

        // Set the session just cause :P
        playerConnection.setName(name);
        playerConnection.setUuid(id);


        /*
        For future reference:

        After LoginSuccess is sent to the client (see above), the Game (client) switches to the PLAY
        state, meaning that after login success, I should be using things related to PLAY
        So i should of been using the PLAY set compression packet.


        LoginSetCompressionPacket packet1 = new LoginSetCompressionPacket();
        packet1.setThreshhold(-1);
        session.sendPacket(packet1);
         */

        if (playerConnection.getProtocol() > 5) {
            PlaySetCompressionPacket setCompression = new PlaySetCompressionPacket();
            setCompression.setThreshhold(-1);
            playerConnection.sendPacket(setCompression);
        }

        PlayDisconnectPacket disconnect = new PlayDisconnectPacket();

        JsonObject reason = new JsonObject();
        {
            reason.addProperty("text",
                "\u00A77Reason: \u00A7cProxy server is offline.\n\n\u00A77Name:\u00A7r "
                    + playerConnection.getName() + "\n \u00A77UUID:\u00A7r " + playerConnection
                    .getUuid().toString() + "\n\n\n");
        }

        disconnect.setReason(reason);

        playerConnection.sendPacket(disconnect);


        //        session.redirect(new InetSocketAddress("localhost", 25566));

    }


}
