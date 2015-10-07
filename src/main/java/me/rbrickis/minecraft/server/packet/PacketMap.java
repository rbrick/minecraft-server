package me.rbrickis.minecraft.server.packet;

import lombok.Data;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

public class PacketMap {

    private final Map<Integer, PacketEntry> idToPacket = new HashMap<>();
    private final Map<Class<? extends Packet>, Integer> packetToId = new HashMap<>();


    public void register(Class<? extends Packet> packetClazz) {
        if (!packetClazz.isAnnotationPresent(PacketInfo.class)) {
            throw new IllegalArgumentException(
                "All packets must be marked with the PacketInfo annotation");
        }
        PacketInfo info = packetClazz.getAnnotation(PacketInfo.class);

        State state = info.state();
        int id = info.id();
        String wiki = info.info();
        Direction direction = info.direction();

        PacketEntry entry = new PacketEntry(packetClazz, state, id, wiki, direction);

        idToPacket.put(id, entry);
        packetToId.put(packetClazz, id);
    }

    public PacketEntry fromId(int id) {
        if (!idToPacket.containsKey(id)) {
            return null;
        }
        return idToPacket.get(id);
    }

    public int getId(Class<? extends Packet> packetClazz) {
        if (!packetToId.containsKey(packetClazz)) {
            return -1;
        }
        return packetToId.get(packetClazz);
    }


    @Data
    public class PacketEntry {
        @NonNull Class<? extends Packet> packet;
        @NonNull private State state;
        @NonNull private int id;
        @NonNull private String info;
        @NonNull private Direction direction;
    }


}
