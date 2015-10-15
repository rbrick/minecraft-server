package me.rbrickis.minecraft.server.packet;

public enum Direction {

    /**
     * Serverbound = Packets being received from the client
     */
    SERVERBOUND,

    /**
     * Clientbound = Packets being sent to the client
     */
    CLIENTBOUND,


    /**
     * Common packets shared between both the client and the server
     * (The server both sends, and reads these packets)
     */
    SHARED

}
