package net.nithium.lib.capella.npc;

import lombok.Getter;
import net.minestom.server.entity.*;
import net.minestom.server.network.packet.server.play.EntityMetaDataPacket;
import net.minestom.server.network.packet.server.play.PlayerInfoRemovePacket;
import net.minestom.server.network.packet.server.play.PlayerInfoUpdatePacket;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Map;

@Getter
public class FakePlayer extends Entity {
    private final String name;
    private final boolean listed;

    private final PlayerSkin skin;

    public FakePlayer(String name, boolean listed) {
        super(EntityType.PLAYER);
        this.name = name;
        this.listed = listed;

        skin = PlayerSkin.fromUsername(name);
    }

    @Override
    public void updateNewViewer(@NotNull Player player) {
        var properties = new ArrayList<PlayerInfoUpdatePacket.Property>();
        properties.add(new PlayerInfoUpdatePacket.Property("textures", skin.textures(), skin.signature()));

        var entry = new PlayerInfoUpdatePacket.Entry(getUuid(), getName(), properties, listed, 0, GameMode.SURVIVAL, null, null);
        super.updateNewViewer(player);
        player.sendPackets(new EntityMetaDataPacket(getEntityId(), Map.of(17, Metadata.Byte((byte) 127))));
    }

    @Override
    public void updateOldViewer(@NotNull Player player) {
        super.updateOldViewer(player);

        player.sendPackets(new PlayerInfoRemovePacket(getUuid()));
    }
}
