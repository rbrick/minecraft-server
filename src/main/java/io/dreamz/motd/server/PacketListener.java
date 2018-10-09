package io.dreamz.motd.server;

import com.google.common.eventbus.Subscribe;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.dreamz.motd.server.api.chat.TextBuilder;
import io.dreamz.motd.server.connection.PlayerConnection;
import io.dreamz.motd.server.packet.State;
import io.dreamz.motd.server.packet.clientbound.login.LoginSetCompressionPacket;
import io.dreamz.motd.server.packet.clientbound.login.LoginSuccessPacket;
import io.dreamz.motd.server.packet.clientbound.play.PlayDisconnectPacket;
import io.dreamz.motd.server.packet.clientbound.status.StatusPongPacket;
import io.dreamz.motd.server.packet.clientbound.status.StatusResponsePacket;
import io.dreamz.motd.server.packet.serverbound.handshake.HandshakePacket;
import io.dreamz.motd.server.packet.serverbound.login.LoginStartPacket;
import io.dreamz.motd.server.packet.serverbound.status.StatusPingPacket;
import io.dreamz.motd.server.packet.serverbound.status.StatusRequestPacket;
import org.bukkit.ChatColor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

public class PacketListener {


    private Config config;

    private JsonObject version = new JsonObject();

    private JsonObject players = new JsonObject();

    {
        players.addProperty("max", 0);
        players.addProperty("online", 0);
        players.add("sample", new JsonArray());
    }

    private JsonObject description = new JsonObject();

    private JsonObject response = new JsonObject();


    public PacketListener(Config config) {
        this.config = config;

        this.version.addProperty("name", ChatColor.translateAlternateColorCodes('&', config.getVersionMessage()));
        this.version.addProperty("protocol", -1);

        this.description.addProperty("text", ChatColor.translateAlternateColorCodes('&', config.getMotd()));


        response.add("version", version);
        response.add("players", players);
        response.add("description", description);

        if (config.hasIcon()) {
            File file = new File(config.getServerIcon());

            if (file.exists()) {
                try {
                    byte[] data = Files.readAllBytes(Paths.get(file.toURI()));

                    this.response.addProperty("favicon", Main.FAVICON_PREFIX + Base64.getEncoder().encodeToString(data));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    @Subscribe
    public void onHandshake(HandshakePacket handshake) {
        State state = handshake.getNextState();
        PlayerConnection playerConnection = handshake.getPlayerConnection();
        playerConnection.setCurrentState(state);
        playerConnection.setProtocol(handshake.getProtocol());

//        if (state == State.LOGIN) {

//        }

    }


    @Subscribe
    public void onStatusRequest(StatusRequestPacket request) {
        PlayerConnection playerConnection = request.getPlayerConnection();

        StatusResponsePacket packet = new StatusResponsePacket();
        packet.setStatus(response);
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
            LoginSetCompressionPacket setCompression = new LoginSetCompressionPacket();
            setCompression.setThreshhold(0);
            playerConnection.sendPacket(setCompression);
        }

        PlayDisconnectPacket disconnect = new PlayDisconnectPacket();

        disconnect.setReason(
                TextBuilder.of(ChatColor.translateAlternateColorCodes('&', config.getDisconnectMessage()))
                        .build().toJson());

        playerConnection.sendPacket(disconnect);
    }


}
