package me.rbrickis.minecraft.server;

import com.google.common.eventbus.Subscribe;
import com.google.gson.JsonObject;
import me.rbrickis.minecraft.server.packet.State;
import me.rbrickis.minecraft.server.packet.clientbound.login.LoginDisconnectPacket;
import me.rbrickis.minecraft.server.packet.clientbound.status.StatusPongPacket;
import me.rbrickis.minecraft.server.packet.clientbound.status.StatusResponsePacket;
import me.rbrickis.minecraft.server.packet.serverbound.handshake.HandshakePacket;
import me.rbrickis.minecraft.server.packet.serverbound.login.LoginStartPacket;
import me.rbrickis.minecraft.server.packet.serverbound.status.StatusPingPacket;
import me.rbrickis.minecraft.server.packet.serverbound.status.StatusRequestPacket;
import me.rbrickis.minecraft.server.session.Session;

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

        System.out.println("Test");

        JsonObject status = new JsonObject();
        {
            JsonObject version = new JsonObject();
            {
                version.addProperty("name", "YOU OUT OF DATE, BITCH!");
                version.addProperty("protocol", 47);
            }

            JsonObject players = new JsonObject();
            {
                players.addProperty("max", -1337);
                players.addProperty("online", 9001);
            }

            JsonObject description = new JsonObject();
            {
                description.addProperty("text",
                    "\u00A74\u00A7l420 \u00A76\u00A7lBlaze \u00A7e\u00A7lit! ");
            }

            status.add("version", version);
            status.add("players", players);
            status.add("description", description);
        }

        StatusResponsePacket packet = new StatusResponsePacket();
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

        LoginDisconnectPacket packet = new LoginDisconnectPacket();

        JsonObject reason = new JsonObject();
        {
            reason.addProperty("text", "I KICKED Y0 ASS");
        }

        packet.setReason(reason);
        session.sendPacket(packet);
    }
}
