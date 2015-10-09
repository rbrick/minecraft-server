package me.rbrickis.minecraft.server.api;

import java.util.UUID;

public interface Player {

    String getName();

    UUID getUniqueId();

    void disconnect(String message);

    default void disconnect() {
        disconnect("You have been disconnected from the server.");
    }

}
