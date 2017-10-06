package me.HeyAwesomePeople.Tycoon.mongodb.listeners;


import com.mongodb.event.ServerHeartbeatFailedEvent;
import com.mongodb.event.ServerHeartbeatStartedEvent;
import com.mongodb.event.ServerHeartbeatSucceededEvent;
import com.mongodb.event.ServerMonitorListener;
import lombok.NoArgsConstructor;
import me.HeyAwesomePeople.Tycoon.utils.Debug;
import me.HeyAwesomePeople.Tycoon.utils.DebugType;

@NoArgsConstructor public class ServerConnection implements ServerMonitorListener {

    @Override
    public void serverHearbeatStarted(ServerHeartbeatStartedEvent serverHeartbeatStartedEvent) {
        // Debug.debug(DebugType.INFO, "Heartbeat with MongoDB server started!");
    }

    @Override
    public void serverHeartbeatSucceeded(ServerHeartbeatSucceededEvent serverHeartbeatSucceededEvent) {

    }

    @Override
    public void serverHeartbeatFailed(ServerHeartbeatFailedEvent serverHeartbeatFailedEvent) {
        Debug.debug(DebugType.ERROR, "Heartbeat to MongoDB server failed!");
    }
}
