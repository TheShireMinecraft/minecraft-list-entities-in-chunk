package us.shirecraft.entityhelper;

import org.bukkit.plugin.java.JavaPlugin;
import us.shirecraft.entityhelper.commands.ListEntitiesCommand;

public final class EntityHelper extends JavaPlugin {
    @Override
    public void onEnable() {
        var listEntitiesCommand = this.getCommand("listentities");
        if(listEntitiesCommand != null)
        {
            listEntitiesCommand.setExecutor(new ListEntitiesCommand());
        }

        getLogger().info("Minecraft entity helper plugin has been enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("Minecraft entity helper plugin has been disabled");
    }
}
