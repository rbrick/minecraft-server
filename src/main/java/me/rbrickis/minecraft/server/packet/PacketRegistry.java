package me.rbrickis.minecraft.server.packet;

import me.rbrickis.minecraft.server.packet.clientbound.status.StatusPongPacket;
import me.rbrickis.minecraft.server.packet.clientbound.status.StatusResponsePacket;
import me.rbrickis.minecraft.server.packet.serverbound.handshake.HandshakePacket;
import me.rbrickis.minecraft.server.packet.serverbound.login.LoginStartPacket;
import me.rbrickis.minecraft.server.packet.serverbound.status.StatusPingPacket;
import me.rbrickis.minecraft.server.packet.serverbound.status.StatusRequestPacket;

import java.util.EnumMap;

public final class PacketRegistry {

    private static EnumMap<State, PacketMap> SERVERBOUND =
        new EnumMap<State, PacketMap>(State.class) {{
            put(State.HANDSHAKE, new PacketMap());
            put(State.STATUS, new PacketMap());
            put(State.LOGIN, new PacketMap());
            put(State.PLAY, new PacketMap());
        }};

    private static EnumMap<State, PacketMap> CLIENTBOUND =
        new EnumMap<State, PacketMap>(State.class) {{
            put(State.HANDSHAKE, new PacketMap());
            put(State.STATUS, new PacketMap());
            put(State.LOGIN, new PacketMap());
            put(State.PLAY, new PacketMap());
        }};



    public static void registerServerboundPacket(Class<? extends Packet> packetClazz) {
        if (!packetClazz.isAnnotationPresent(PacketInfo.class)) {
            throw new IllegalArgumentException(
                "All packets must be marked with the PacketInfo annotation");
        }
        State state = packetClazz.getAnnotation(PacketInfo.class).state();
        PacketMap stateMap = SERVERBOUND.get(state);
        stateMap.register(packetClazz);
    }

    public static void registerClientboundPacket(Class<? extends Packet> packetClazz) {
        if (!packetClazz.isAnnotationPresent(PacketInfo.class)) {
            throw new IllegalArgumentException(
                "All packets must be marked with the PacketInfo annotation");
        }
        State state = packetClazz.getAnnotation(PacketInfo.class).state();
        PacketMap stateMap = CLIENTBOUND.get(state);
        stateMap.register(packetClazz);
    }


    /**
     * Gets the PacketMap from the State
     */
    public static PacketMap fromState(Direction direction, State state) {
        switch (direction) {
            case SERVERBOUND: {
                return SERVERBOUND.get(state);
            }
            case CLIENTBOUND: {
                return CLIENTBOUND.get(state);
            }
            default: {
                return null;
            }
        }
    }


    static {
        // Serverbound packets

        // Handshake
        registerServerboundPacket(HandshakePacket.class);

        // Status
        registerServerboundPacket(StatusRequestPacket.class);
        registerServerboundPacket(StatusPingPacket.class);

        // Login
        registerServerboundPacket(LoginStartPacket.class);


        // Clientbound

        // Status
        registerClientboundPacket(StatusResponsePacket.class);
        registerClientboundPacket(StatusPongPacket.class);

        // Login

    }

}
