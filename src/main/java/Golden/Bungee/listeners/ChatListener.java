package Golden.Bungee.listeners;

import Golden.Bungee.Main;
import Golden.Bungee.utils.ChatUtils;
import Golden.Bungee.utils.LuckPermsUtils;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(ChatEvent event) {
        if (!(event.getSender() instanceof ProxiedPlayer)) return;
        if (event.isCommand()) return;

        ProxiedPlayer player = (ProxiedPlayer) event.getSender();
        String message = event.getMessage();

        if (Main.getInstance().getChatManager().isStaffChatToggled(player.getUniqueId())) {
            event.setCancelled(true);
            sendStaffChat(player, message);
        } else if (Main.getInstance().getChatManager().isAdminChatToggled(player.getUniqueId())) {
            event.setCancelled(true);
            sendAdminChat(player, message);
        }
    }

    public static void sendStaffChat(ProxiedPlayer sender, String message) {
        String format = Main.getInstance().getConfigManager().getMessage("staffchat-format");
        
        String prefix = LuckPermsUtils.getPrefix(sender);
        String server = sender.getServer().getInfo().getName();
        String rank = LuckPermsUtils.getGroupName(sender);
        
        TextComponent nameComponent = new TextComponent(ChatUtils.color(prefix + sender.getName()));
        nameComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatUtils.color("&7Server: &e" + server + "\n&7Rank: &f" + rank)).create()));
            
        String[] parts = format.split("%player%");
        ComponentBuilder builder = new ComponentBuilder("");
        
        if (parts.length > 0) {
            builder.append(ChatUtils.color(parts[0]));
        }
        
        builder.append(nameComponent);
        
        if (parts.length > 1) {
            builder.append(ChatUtils.color(parts[1].replace("%message%", message)));
        }
        
        for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
            if (player.hasPermission("gbu.staff.chat")) {
                player.sendMessage(builder.create());
            }
        }
    }

    public static void sendAdminChat(ProxiedPlayer sender, String message) {
        String format = Main.getInstance().getConfigManager().getMessage("adminchat-format");
        
        String prefix = LuckPermsUtils.getPrefix(sender);
        String server = sender.getServer().getInfo().getName();
        String rank = LuckPermsUtils.getGroupName(sender);
        
        TextComponent nameComponent = new TextComponent(ChatUtils.color(prefix + sender.getName()));
        nameComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, 
            new ComponentBuilder(ChatUtils.color("&7Server: &e" + server + "\n&7Rank: &f" + rank)).create()));
            
        String[] parts = format.split("%player%");
        ComponentBuilder builder = new ComponentBuilder("");
        
        if (parts.length > 0) {
            builder.append(ChatUtils.color(parts[0]));
        }
        
        builder.append(nameComponent);
        
        if (parts.length > 1) {
            builder.append(ChatUtils.color(parts[1].replace("%message%", message)));
        }
        
        for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
            if (player.hasPermission("gbu.admin.chat")) {
                player.sendMessage(builder.create());
            }
        }
    }
}
