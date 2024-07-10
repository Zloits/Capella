package net.nithium.lib.capella.npc;

import lombok.NonNull;

import java.io.File;
import java.util.List;

public interface NpcManager {

    FakePlayer loadNpc(@NonNull File file);

    void saveNpc(@NonNull File file, @NonNull FakePlayer fakePlayer);

    List<FakePlayer> getNpcs();

    FakePlayer getNpc(@NonNull String name);

    FakePlayer getNpc(@NonNull File file);
}
