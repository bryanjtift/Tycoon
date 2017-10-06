package me.HeyAwesomePeople.Tycoon.mongodb.listeners;

import com.mongodb.event.CommandFailedEvent;
import com.mongodb.event.CommandStartedEvent;
import com.mongodb.event.CommandSucceededEvent;
import lombok.NoArgsConstructor;
import me.HeyAwesomePeople.Tycoon.utils.Debug;
import me.HeyAwesomePeople.Tycoon.utils.DebugType;

/**
 * @author HeyAwesomePeople
 * @since Friday, September 29 2017
 */
@NoArgsConstructor public class CommandListener implements com.mongodb.event.CommandListener {

    @Override
    public void commandStarted(CommandStartedEvent event) {
        Debug.debug(DebugType.INFO, String.format("Sent command '%s:%s' with id %s to database '%s' "
                        + "on connection '%s' to server '%s'",
                event.getCommandName(),
                event.getCommand().get(event.getCommandName()),
                event.getRequestId(),
                event.getDatabaseName(),
                event.getConnectionDescription()
                        .getConnectionId(),
                event.getConnectionDescription().getServerAddress()));
    }

    @Override
    public void commandSucceeded(CommandSucceededEvent event) {
        Debug.debug(DebugType.INFO, String.format("Successfully executed command '%s' with id %s "
                        + "on connection '%s' to server '%s'",
                event.getCommandName(),
                event.getRequestId(),
                event.getConnectionDescription()
                        .getConnectionId(),
                event.getConnectionDescription().getServerAddress()));
    }

    @Override
    public void commandFailed(CommandFailedEvent event) {
        Debug.debug(DebugType.ERROR, String.format("Failed execution of command '%s' with id %s "
                        + "on connection '%s' to server '%s' with exception '%s'",
                event.getCommandName(),
                event.getRequestId(),
                event.getConnectionDescription()
                        .getConnectionId(),
                event.getConnectionDescription().getServerAddress(),
                event.getThrowable()));
    }
}
