package me.rbrickis.minecraft.server.packet.clientbound.play;

import io.netty.buffer.ByteBuf;
import lombok.Setter;
import me.rbrickis.minecraft.server.netty.BufferUtils;
import me.rbrickis.minecraft.server.packet.ClientboundPacket;
import me.rbrickis.minecraft.server.packet.Direction;
import me.rbrickis.minecraft.server.packet.PacketInfo;
import me.rbrickis.minecraft.server.packet.State;



@Setter
@PacketInfo(
    id = 0x01,
    direction = Direction.CLIENTBOUND,
    state = State.PLAY,
    info = "http://wiki.vg/Protocol#Join_Game")
public class PlayJoinGamePacket extends ClientboundPacket {

    private int entityId;
    private int gamemode; // |= 0x8 for hardcore
    private int dimension;
    private int difficulty;
    private int maxPlayers;
    private String levelType;
    private boolean reducedInfo;


    @Override
    public void encode(ByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeByte(gamemode);
        buf.writeByte(difficulty);
        buf.writeByte(difficulty);
        buf.writeByte(maxPlayers);
        BufferUtils.writeString(buf, levelType);
        buf.writeBoolean(reducedInfo);
    }

    public void setHardcore() {
        gamemode |= 0x8;
    }



}
