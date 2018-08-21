package io.dreamz.motd.server.netty;

import io.netty.buffer.ByteBuf;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public final class BufferUtils {

    public static int readVarInt(ByteBuf in) {
        int i = 0;
        int j = 0;
        while (true) {
            int k = in.readByte();
            i |= (k & 0x7F) << j++ * 7;
            if (j > 5)
                throw new RuntimeException("VarInt too big");
            if ((k & 0x80) != 128)
                break;
        }
        return i;
    }

    public static void writeVarInt(ByteBuf out, int paramInt) {
        while (true) {
            if ((paramInt & 0xFFFFFF80) == 0) {
                out.writeByte(paramInt);
                return;
            }
            out.writeByte(paramInt & 0x7F | 0x80);
            paramInt >>>= 7;
        }
    }

    public static void writeString(ByteBuf out, String string) {
        byte[] bytes = string.getBytes(StandardCharsets.UTF_8);
        int length = bytes.length;
        writeVarInt(out, length);
        out.writeBytes(bytes);
    }

    public static String readString(ByteBuf in) {
        int length = readVarInt(in);        // read the length
        byte[] data = new byte[length];     // Allocate the correct length
        in.readBytes(data);                 // read the bytes to the new byte array
        return new String(data, StandardCharsets.UTF_8); // create a new UTF-8 encoded string
    }

    public static void writeUUID(ByteBuf out, UUID id) {
        out.writeLong(id.getMostSignificantBits());
        out.writeLong(id.getLeastSignificantBits());
    }

    public static UUID readUUID(ByteBuf in) {
        long mostSignificant = in.readLong();
        long leastSignificant = in.readLong();
        return new UUID(mostSignificant, leastSignificant);
    }

    private BufferUtils() {
    }
}
