package us.shirecraft.entityhelper.commands;

import org.bukkit.command.Command;
import org.bukkit.command.ConsoleCommandSender;
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
}
