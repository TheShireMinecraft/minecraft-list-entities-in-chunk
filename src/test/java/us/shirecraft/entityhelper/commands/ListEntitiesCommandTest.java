package us.shirecraft.entityhelper.commands;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class ListEntitiesCommandTest {
    @Test
    void onCommand__should_reject_non_player_senders() {
        // Arrange
        var systemUnderTest = new ListEntitiesCommand();
        var sender = mock(ConsoleCommandSender.class);

        // Act
        systemUnderTest.onCommand(sender, mock(Command.class), "", new String[0]);

        // Assert
        verify(sender).sendMessage("This command may only be used by players");
    }

    @Test
    void onCommand__should_reject_players_who_are_not_server_operators() {
        // Arrange
        var systemUnderTest = new ListEntitiesCommand();
        var sender = mock(Player.class);
        when(sender.isOp()).thenReturn(false);

        // Act
        systemUnderTest.onCommand(sender, mock(Command.class), "", new String[0]);

        // Assert
        verify(sender).sendMessage("This command may only be used by server operators");
    }

    @Test
    //void onCommand__should_error_on_invalid_limit_input() {
    void onCommand__should_tell_user_no_entities_found_in_current_chunk() {
        // Arrange
        var systemUnderTest = new ListEntitiesCommand();
        var sender = mock(Player.class);
        var chunk = mock(Chunk.class);
        when(sender.isOp()).thenReturn(true);
        when(sender.getChunk()).thenReturn(chunk);
        when(chunk.getX()).thenReturn(40);
        when(chunk.getZ()).thenReturn(70);
        when(chunk.getEntities()).thenReturn(new Entity[0]);

        // Act
        systemUnderTest.onCommand(sender, mock(Command.class), "", new String[0]); //new String[] { "This is... not-a-number!" }

        // Assert
        verify(sender).sendMessage("No entities could be found in the current chunk (40,70)");
    }

    @Test
    void onCommand__should_error_on_invalid_limit_input() {
        // Arrange
        var systemUnderTest = new ListEntitiesCommand();
        var sender = mock(Player.class);
        var chunk = mock(Chunk.class);
        var mockEntity = mock(Entity.class);
        when(sender.isOp()).thenReturn(true);
        when(sender.getChunk()).thenReturn(chunk);
        when(chunk.getEntities()).thenReturn(new Entity[] { mockEntity });

        // Act
        systemUnderTest.onCommand(sender, mock(Command.class), "", new String[] { "This is... not-a-number!" });

        // Assert
        verify(sender).sendMessage("The limit must be numeric. Please try again with a different limit, or run the command without specifying a limit.");
    }

    @Test
    void onCommand__should_output_entity_info() {
        // Arrange
        var systemUnderTest = new ListEntitiesCommand();
        var sender = mock(Player.class);
        var chunk = mock(Chunk.class);
        var mockEntity1 = mock(Entity.class);
        var mockEntity2 = mock(Entity.class);
        var mockLocation = mock(Location.class);
        when(mockLocation.getBlockX()).thenReturn(-30);
        when(mockLocation.getBlockY()).thenReturn(72);
        when(mockLocation.getBlockZ()).thenReturn(4);
        when(mockEntity1.getLocation()).thenReturn(mockLocation);
        when(mockEntity2.getLocation()).thenReturn(mockLocation);
        when(mockEntity1.getType()).thenReturn(EntityType.BOAT);
        when(mockEntity1.getName()).thenReturn("Boaty McBoatface");
        when(mockEntity2.getType()).thenReturn(EntityType.VILLAGER);
        when(mockEntity2.getName()).thenReturn("Villager McVillagerface");
        when(sender.isOp()).thenReturn(true);
        when(sender.getChunk()).thenReturn(chunk);
        when(chunk.getEntities()).thenReturn(new Entity[] { mockEntity1, mockEntity2 });

        // Act
        systemUnderTest.onCommand(sender, mock(Command.class), "", new String[0]);

        // Assert
        verify(sender).sendMessage("There are 2 entities in the current chunk. (0,0)");
        verify(sender).sendMessage("Up to 15 of them are listed below...");
        verify(sender).sendMessage("1> BOAT Boaty McBoatface - Loc: (-30,72,4)");
        verify(sender).sendMessage("2> VILLAGER Villager McVillagerface - Loc: (-30,72,4)");
        verify(sender).sendMessage("");
    }
}
