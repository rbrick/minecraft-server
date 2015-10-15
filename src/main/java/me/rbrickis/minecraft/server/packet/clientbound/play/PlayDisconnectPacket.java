package me.rbrickis.minecraft.server.packet.clientbound.play;

import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import lombok.Setter;
import me.rbrickis.minecraft.server.netty.BufferUtils;
import me.rbrickis.minecraft.server.packet.ClientboundPacket;
import me.rbrickis.minecraft.server.packet.Direction;
import me.rbrickis.minecraft.server.packet.PacketInfo;
import me.rbrickis.minecraft.server.packet.State;

@Setter
@PacketInfo(
    id = 0x40,
    direction = Direction.CLIENTBOUND,
    info = "http://wiki.vg/Protocol#Disconnect",
    state = State.PLAY)
public class PlayDisconnectPacket extends ClientboundPacket {

    private JsonObject reason;

    @Override
    public void encode(ByteBuf buf) {
        BufferUtils.writeString(buf, reason.toString());
    }
}
