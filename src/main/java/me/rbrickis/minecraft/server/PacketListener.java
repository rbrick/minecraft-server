package me.rbrickis.minecraft.server;

import com.google.common.escape.Escaper;
import com.google.common.escape.Escapers;
import com.google.common.eventbus.Subscribe;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import me.rbrickis.minecraft.server.packet.State;
import me.rbrickis.minecraft.server.packet.clientbound.login.LoginSetCompressionPacket;
import me.rbrickis.minecraft.server.packet.clientbound.login.LoginSuccessPacket;
import me.rbrickis.minecraft.server.packet.clientbound.status.StatusPongPacket;
import me.rbrickis.minecraft.server.packet.clientbound.status.StatusResponsePacket;
import me.rbrickis.minecraft.server.packet.serverbound.handshake.HandshakePacket;
import me.rbrickis.minecraft.server.packet.serverbound.login.LoginStartPacket;
import me.rbrickis.minecraft.server.packet.serverbound.status.StatusPingPacket;
import me.rbrickis.minecraft.server.packet.serverbound.status.StatusRequestPacket;
import me.rbrickis.minecraft.server.session.Session;

import java.util.UUID;

public class PacketListener {

    @Subscribe
    public void onHandshake(HandshakePacket handshake) {
        System.out.println("Protocol : " + handshake.getProtocol());
        System.out.println("Address  : " + handshake.getAddress());
        System.out.println("Port     : " + handshake.getPort());
        System.out.println("State    : " + handshake.getNextState());

        State state = handshake.getNextState();
        Session session = handshake.getSession();
        session.setCurrentState(state);
    }

    @Subscribe
    public void onStatusRequest(StatusRequestPacket request) {
        Session session = request.getSession();
        String motd = "§4§l420 §6§lBlaze §e§lit!";
        Escaper escaper = Escapers.builder().addEscape('§', "\u00A7").build();

        JsonObject status = new JsonObject();
        {
            JsonObject version = new JsonObject();
            {
                version.addProperty("name", "YOU OUT OF DATE, BITCH!");
                version.addProperty("protocol", 5);
            }

            JsonObject players = new JsonObject();
            {
                players.addProperty("max", -1337);
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
        session.sendPacket(packet);
    }


    @Subscribe
    public void onStatusPing(StatusPingPacket ping) {
        Session session = ping.getSession();
        long payload = ping.getPayload();
        StatusPongPacket pong = new StatusPongPacket();
        pong.setPayload(payload);
        session.sendPacket(pong);
    }

    @Subscribe
    public void onLoginStart(LoginStartPacket loginStart) {
        Session session = loginStart.getSession();

        String name = loginStart.getName();

        LoginSuccessPacket packet = new LoginSuccessPacket();
        packet.setId(UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes()));
        packet.setName(name);

        session.sendPacket(packet);

        LoginSetCompressionPacket packet1 = new LoginSetCompressionPacket();
        packet1.setThreshhold(-1);

        session.sendPacket(packet1);

    }
}
