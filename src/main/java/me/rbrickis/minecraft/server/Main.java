package me.rbrickis.minecraft.server;

import me.rbrickis.minecraft.server.impl.MinecraftServer;

public class Main {

    public static void main(String... args) {
        MinecraftServer server = new MinecraftServer(25565);
        server.getEventBus().register(new PacketListener());
        server.start();
    }
}
