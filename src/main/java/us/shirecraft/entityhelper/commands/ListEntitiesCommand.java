package us.shirecraft.entityhelper.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ListEntitiesCommand implements CommandExecutor {
    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            String[] args
    ) {
        if(!(sender instanceof Player player))
        {
            sender.sendMessage("This command may only be used by players");
            return true; // Suppress usage information by returning true
        }

        if(!player.isOp())
        {
            sender.sendMessage("This command may only be used by server operators");
            return true; // Suppress usage information by returning true
        }

        var chunk = player.getChunk();
        var chunkLocation = "(" + chunk.getX() + "," + chunk.getZ() + ")";
        var entities = chunk.getEntities();
        var thereAreNoEntities = entities.length == 0;

        if(thereAreNoEntities)
        {
            sender.sendMessage("No entities could be found in the current chunk " + chunkLocation);
            return true;
        }

        int limiter = 15;
        sender.sendMessage("There are " + (entities.length + 1) + " entities in the current chunk. " + chunkLocation);
        sender.sendMessage("Up to " + limiter + " of them are listed below...");
        for (int i = 0; i < entities.length; i++) {
            if(i > limiter) break;
            var entity = entities[i];
            var entityLocation = "(" + entity.getLocation().getBlockX() + "," + entity.getLocation().getBlockY() + "," + entity.getLocation().getBlockZ() + ")";
            sender.sendMessage((i+1) + "> " + entity.getType().name() + " " + entity.getName() + " - Loc: " + entityLocation);
            limiter++;
        }
        sender.sendMessage("");

        return true;
    }
}
