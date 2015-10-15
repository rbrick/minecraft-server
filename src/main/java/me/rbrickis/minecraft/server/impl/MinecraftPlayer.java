package me.rbrickis.minecraft.server.impl;

import me.rbrickis.minecraft.server.api.Player;
import me.rbrickis.minecraft.server.connection.PlayerConnection;

import java.util.UUID;


/**
 * If later on I want to create a proper API or anything of the sorts
 * i will, i am currently just fucking around
 */
public class MinecraftPlayer implements Player {

    private PlayerConnection playerConnection;

    private String name;
    private UUID uuid;


    public MinecraftPlayer(String name, UUID uuid, PlayerConnection playerConnection) {
        this.playerConnection = playerConnection;
        this.uuid = uuid;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public UUID getUniqueId() {
        return uuid;
    }

    @Override
    public void disconnect(String message) {

    }
}
