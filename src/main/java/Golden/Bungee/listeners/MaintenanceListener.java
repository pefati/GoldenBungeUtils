package Golden.Bungee.listeners;

import Golden.Bungee.Main;
import Golden.Bungee.utils.ChatUtils;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.event.EventHandler;

import java.util.List;

public class MaintenanceListener implements Listener {

    @EventHandler
    public void onLogin(PostLoginEvent event) {
        Configuration config = Main.getInstance().getConfigManager().getConfig();
        if (config.getBoolean("maintenance.enabled")) {
            List<String> whitelist = config.getStringList("maintenance.whitelist");
            if (!whitelist.contains(event.getPlayer().getName())) {
                event.getPlayer().disconnect(ChatUtils.color(Main.getInstance().getConfigManager().getMessage("maintenance-kick")));
            }
        }
    }
}
