package io.dreamz.motd.server.api;

public interface Server {

    String getName();


    String getVersion();


    int getProtocol();


    void start();


    void shutdown();
}
