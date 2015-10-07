package me.rbrickis.minecraft.server.api;

public interface Server {

    String getName();


    String getVersion();


    int getProtocol();


    void start();


    void shutdown();
}
