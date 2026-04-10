package Golden.Bungee.utils;

import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionManager implements Listener {

    private final Map<UUID, Long> loginTimes = new HashMap<>();

    @EventHandler
    public void onLogin(PostLoginEvent event) {
        loginTimes.put(event.getPlayer().getUniqueId(), System.currentTimeMillis());
    }

    @EventHandler
    public void onDisconnect(PlayerDisconnectEvent event) {
        loginTimes.remove(event.getPlayer().getUniqueId());
    }

    public long getSessionTime(UUID uuid) {
        if (!loginTimes.containsKey(uuid)) return 0;
        return System.currentTimeMillis() - loginTimes.get(uuid);
    }
}
