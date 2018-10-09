package io.dreamz.motd.server.packet.clientbound.play;

import io.dreamz.motd.server.netty.BufferUtils;
import io.dreamz.motd.server.packet.ClientboundPacket;
import io.dreamz.motd.server.packet.Direction;
import io.dreamz.motd.server.packet.PacketInfo;
import io.dreamz.motd.server.packet.State;
import io.netty.buffer.ByteBuf;
import lombok.Setter;




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
