package net.nithium.test;

import net.kyori.adventure.text.Component;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.inventory.InventoryType;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.thread.Acquirable;
import net.nithium.lib.capella.Capella;
import net.nithium.lib.capella.menu.Menu;
import net.nithium.lib.capella.menu.MenuItem;
import net.nithium.lib.capella.menu.MenuManager;

import java.io.File;

public class Core {

    public static void main(String[] args) {
        MinecraftServer minecraftServer = MinecraftServer.init();
        Capella capella = Capella.init();

        Acquirable<MenuManager> menuManager = Acquirable.of(capella.getMenuManager());
        menuManager.sync(manager -> {
            File folder = new File("/menus/");

            File[] files = folder.listFiles();
            for (File file : files) {
                manager.loadMenu(file);
            }
        });

        if (capella.getMenuManager().getMenu(10) == null) {
            Menu menu = Menu.create(10, Component.text("title"), InventoryType.CHEST_6_ROW);
            menu.fillEdgeMenu(Material.GRAY_STAINED_GLASS);

            menu.setItem(MenuItem.builder()
                    .material(Material.DIAMOND_SWORD)
                    .slot(27)
                    .amount(1)
                    .onClick(event -> {
                        final Player player = event.getPlayer();

                        player.closeInventory();
                        player.getInventory().addItemStack(ItemStack.of(Material.DIAMOND_SWORD));
                    })
                    .build());

            menuManager.sync(manager -> {
                menu.init(); // Always last with init() method to register every item listener

                File file = new File("/menus/" + menu.getMenuId() + ".json");
                manager.saveMenu(file, menu);
            });
        }

        minecraftServer.start("0.0.0.0", 25565);
    }
}
