package Golden.Bungee.listeners;

import Golden.Bungee.Main;
import Golden.Bungee.utils.ChatUtils;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class StaffNotificationListener implements Listener {

    @EventHandler
    public void onLogin(PostLoginEvent event) {
        if (event.getPlayer().hasPermission("gbu.staff.chat")) {
            Main.getInstance().getChatManager().setStaffChat(event.getPlayer().getUniqueId(), true);
        } else {
            Main.getInstance().getChatManager().setStaffChat(event.getPlayer().getUniqueId(), false);
        }

        if (!event.getPlayer().hasPermission("gbu.admin.chat")) {
            Main.getInstance().getChatManager().setAdminChat(event.getPlayer().getUniqueId(), false);
        }

        if (event.getPlayer().hasPermission("gbu.staff.notify")) {
            String format = Main.getInstance().getConfigManager().getMessage("staff-join");
            String message = format.replace("%player%", event.getPlayer().getName());
            broadcastToStaff(message);
        }
    }

    @EventHandler
    public void onDisconnect(PlayerDisconnectEvent event) {
        if (event.getPlayer().hasPermission("gbu.staff.notify")) {
            String format = Main.getInstance().getConfigManager().getMessage("staff-quit");
            String message = format.replace("%player%", event.getPlayer().getName());
            broadcastToStaff(message);
        }
    }

    @EventHandler
    public void onSwitch(ServerSwitchEvent event) {
        if (event.getPlayer().hasPermission("gbu.staff.notify")) {
            if (event.getFrom() == null) return; 
            
            String format = Main.getInstance().getConfigManager().getMessage("staff-switch");
            String message = format.replace("%player%", event.getPlayer().getName()).replace("%server%", event.getPlayer().getServer().getInfo().getName());
            broadcastToStaff(message);
        }
    }

    private void broadcastToStaff(String message) {
        for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
            if (p.hasPermission("gbu.staff.notify")) {
                p.sendMessage(ChatUtils.color(message));
            }
        }
    }
}
