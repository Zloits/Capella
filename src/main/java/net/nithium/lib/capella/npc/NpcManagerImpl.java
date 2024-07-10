package net.nithium.lib.capella.npc;

import lombok.Cleanup;
import lombok.NonNull;
import lombok.SneakyThrows;
import net.nithium.lib.capella.Capella;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class NpcManagerImpl implements NpcManager {
    private File folder = new File("/npcs/");
    private List<FakePlayer> npcs = new ArrayList<>();

    public NpcManagerImpl() {
        if (!folder.exists()) {
            folder.mkdir();
        }
    }

    @Override
    public FakePlayer loadNpc(@NonNull File file) {
        if (!file.exists()) {
            throw new NullPointerException("File: " + file.getAbsolutePath() + " does not exist");
        }

        try {
            @Cleanup BufferedReader reader = new BufferedReader(new FileReader(file));

            FakePlayer fakePlayer = Capella.GSON.fromJson(reader, FakePlayer.class);
            npcs.add(fakePlayer);

            Capella.LOGGER.info("Successfully load npc: " + fakePlayer.getName());

            return fakePlayer;
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void saveNpc(@NonNull File file, @NonNull FakePlayer fakePlayer) {
        if (!file.exists()) {
            createFile(file);
        }

        String json = Capella.GSON.toJson(fakePlayer);
        try {
            @Cleanup FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(json);

            Capella.LOGGER.info("Successfully saved npc: " + fakePlayer.getName());
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<FakePlayer> getNpcs() {
        return npcs;
    }

    @Override
    public FakePlayer getNpc(@NonNull String name) {
        for (FakePlayer player : npcs) {
            if (player.getName().equals(name)) {
                return player;
            }
        } return null;
    }

    @Override
    public FakePlayer getNpc(@NonNull File file) {
        if (!file.exists()) {
            throw new NullPointerException("File:" + file.getAbsoluteFile() +" does not exist");
        }

        FakePlayer fakePlayer = null;
        try {
            @Cleanup BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            fakePlayer = Capella.GSON.fromJson(bufferedReader, FakePlayer.class);
        }catch (IOException e) {
            e.printStackTrace();
        }

        return fakePlayer;
    }

    @SneakyThrows(IOException.class)
    private void createFile(@NonNull File file) {
        file.createNewFile();
    }
}
