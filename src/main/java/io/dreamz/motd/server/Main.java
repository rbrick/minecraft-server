package io.dreamz.motd.server;

import com.google.gson.Gson;
import io.dreamz.motd.server.impl.MinecraftServer;

import java.io.IOException;
import java.net.URISyntaxException;


public class Main {


    public static final Gson GSON = new Gson();


    public static void main(String... args) throws IOException, URISyntaxException {
        MinecraftServer server = new MinecraftServer(25565);
        server.getEventBus().register(new PacketListener(Config.read()));
        server.start();
    }
}
