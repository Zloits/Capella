package net.nithium.lib.capella.menu;

import lombok.Builder;
import lombok.Getter;
import net.minestom.server.event.inventory.InventoryClickEvent;
import net.minestom.server.item.Material;

import java.util.function.Consumer;

@Builder
@Getter
public class MenuItem {
    private Material material;
    private int slot;
    private int amount;

    private Consumer<InventoryClickEvent> onClick;

}
