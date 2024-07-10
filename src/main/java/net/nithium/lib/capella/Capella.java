package net.nithium.lib.capella;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import net.nithium.lib.capella.menu.MenuManager;
import net.nithium.lib.capella.menu.MenuManagerImpl;
import net.nithium.lib.capella.npc.NpcManager;
import net.nithium.lib.capella.npc.NpcManagerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
public class Capella {

    @Setter
    private static Capella instance;

    public static final Gson GSON = new Gson();
    public static final Logger LOGGER = LoggerFactory.getLogger(Capella.class);

    private String serverIdentifierName = "ds001`";

    private MenuManager menuManager;
    private NpcManager npcManager;

    public Capella() {
        menuManager = new MenuManagerImpl();
        npcManager = new NpcManagerImpl();
    }

    public static Capella init() {
        Capella capella = new Capella();

        setInstance(capella);
        return capella;
    }
}
