package net.nithium.lib.capella.menu;

import lombok.Cleanup;
import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import net.nithium.lib.capella.Capella;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MenuManagerImpl implements MenuManager {

    @Getter
    private File folder = new File("/menus/");

    private List<Menu> menus = new ArrayList<>();

    public MenuManagerImpl() {
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    @Override
    public Menu loadMenu(@NonNull File file) {
        if (file == null || !file.exists()) {
            throw new NullPointerException("file is null or doesn't exist");
        }

        try {
            @Cleanup BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

            Menu menu = Capella.GSON.fromJson(bufferedReader, Menu.class);
            menus.add(menu);

            Capella.LOGGER.info("Successfully load menu: " + menu.getMenuId());

            return menu;
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void saveMenu(@NonNull File file, @NonNull Menu menu) {
        if (file == null || !file.exists()) {
            createFile(file);
            Capella.LOGGER.info("Created file: " + file.getName() + " for " + menu.getMenuId() +" since it was not exists.");
        }

        String json = Capella.GSON.toJson(menu);
        try {
            @Cleanup FileWriter writer = new FileWriter(file);
            writer.write(json);

            Capella.LOGGER.info("Successfully save menu: " + menu.getMenuId());
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Menu> getMenus() {
        return menus;
    }

    @Override
    public Menu getMenu(int id) {
        for (Menu menu : menus) {
            if (menu.getMenuId() == id) {
                return menu;
            }
        } return null;
    }

    @Override
    public Menu getMenu(@NonNull File file) {
        if (!file.exists()) {
            throw new NullPointerException("File: " + file.getAbsoluteFile() + " does not exist");
        }

        Menu menu = null;
        try {
            @Cleanup BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            menu = Capella.GSON.fromJson(bufferedReader, Menu.class);
        }catch (IOException e) {
            e.printStackTrace();
        }

        return menu;
    }

    @SneakyThrows(IOException.class)
    private void createFile(@NonNull File file) {
        file.createNewFile();
    }
}
