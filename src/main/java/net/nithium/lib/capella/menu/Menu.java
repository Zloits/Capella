package net.nithium.lib.capella.menu;

import lombok.*;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.inventory.InventoryClickEvent;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.inventory.InventoryType;
import net.minestom.server.item.ItemComponent;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.sound.SoundEvent;
import net.nithium.lib.capella.Capella;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor(staticName = "create")
@Getter
public class Menu {

    @NonNull
    private int menuId;

    @NonNull
    private Component title;

    @NonNull
    private InventoryType inventoryType;

    private Inventory menu = new Inventory(getInventoryType(), getTitle());
    private int rows = getInventoryType().getSize() / 8;

    private List<MenuItem> items = new ArrayList<>();

    public Menu fillEdgeMenu(@NonNull Material material) {
        for (int row = 0; row < rows; row++) {
            int i = row * 9;
            for (int slot = i; slot < i + 9; slot++) {
                menu.setItemStack(slot, ItemStack.builder(material).set(ItemComponent.ITEM_NAME, Component.text("")).build());
            }
        }
        return this;
    }

    public Menu fillMenu(@NonNull Material material) {
        for (int slot = 0; slot < getInventoryType().getSize(); slot++) {
            menu.setItemStack(slot, ItemStack.builder(material).set(ItemComponent.ITEM_NAME, Component.text("")).build());
        }
        return this;
    }

    public Menu setItem(@NonNull ItemStack itemStack, int slot) {
        if (menu.getItemStack(slot) != null) {
            throw new IllegalStateException("Menu slot" + slot +" already contains an item");
        }

        MenuItem menuItem = MenuItem.builder()
                .material(itemStack.material())
                .amount(itemStack.amount())
                .slot(slot)
                .onClick(event -> event.getSlot())
                .build();
        items.add(menuItem);
        return this;
    }

    public Menu setItem(@NonNull MenuItem menuItem) {
        if (menu.getItemStack(menuItem.getSlot()) != null) {
            throw new IllegalStateException("Menu slot" + menuItem.getSlot() + " already contains an item");
        }

        items.add(menuItem);
        return this;
    }

    public Menu openMenu(@NonNull Player player) {
        player.openInventory(menu);
        player.playSound(Sound.sound(SoundEvent.BLOCK_CHEST_OPEN, Sound.Source.RECORD, 1f, 1f));
        return this;
    }

    public Menu init() {
        for (MenuItem menuItem : items) {
            MinecraftServer.getGlobalEventHandler().addListener(InventoryClickEvent.class, menuItem.getOnClick());
        }

        return this;
    }
}
