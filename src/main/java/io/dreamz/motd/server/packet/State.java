package io.dreamz.motd.server.packet;

public enum State {

    HANDSHAKE, // 0

    STATUS,    // 1

    LOGIN,     // 2

    PLAY;      // 3



    public static State fromInteger(int i) {
        if (i > values().length - 1) {
            throw new IllegalArgumentException("i is too high!");
        }
        return values()[i];
    }
}
