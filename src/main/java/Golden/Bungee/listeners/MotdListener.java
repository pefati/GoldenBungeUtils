package Golden.Bungee.listeners;

import Golden.Bungee.Main;
import Golden.Bungee.utils.ChatUtils;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.event.EventHandler;

import java.util.List;
import java.util.Random;

public class MotdListener implements Listener {

    private final Random random = new Random();

    @EventHandler
    public void onPing(ProxyPingEvent event) {
        Configuration config = Main.getInstance().getConfigManager().getConfig();
        
        if (config.getBoolean("motd.enabled")) {
            List<String> motds = config.getStringList("motd.lines");
            if (!motds.isEmpty()) {
                String motd = motds.get(random.nextInt(motds.size()));
                ServerPing ping = event.getResponse();
                ping.setDescriptionComponent(new net.md_5.bungee.api.chat.TextComponent(ChatUtils.color(motd)));
                event.setResponse(ping);
            }
        }
    }
}
