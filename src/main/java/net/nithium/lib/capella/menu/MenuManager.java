package net.nithium.lib.capella.menu;

import lombok.NonNull;

import java.io.File;
import java.util.List;

public interface MenuManager {

    Menu loadMenu(@NonNull File file);

    void saveMenu(@NonNull File file, @NonNull Menu menu);

    List<Menu> getMenus();

    Menu getMenu(int id);

    Menu getMenu(@NonNull File file);
}
