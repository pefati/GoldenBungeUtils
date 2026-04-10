package Golden.Bungee.commands.staff;

import Golden.Bungee.Main;
import Golden.Bungee.utils.ChatUtils;
import Golden.Bungee.utils.LuckPermsUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.ArrayList;
import java.util.List;

public class StaffListCommand extends Command {

    public StaffListCommand() {
        super("stafflist", "gbu.staff.list");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(ChatUtils.color(Main.getInstance().getConfigManager().getMessage("only-players")));
            return;
        }

        List<ProxiedPlayer> staff = new ArrayList<>();
        for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
            if (p.hasPermission("gbu.staff.list")) {
                staff.add(p);
            }
        }

        String msg = Main.getInstance().getConfigManager().getMessage("staff-list");
        if (msg == null || msg.isEmpty()) return;

        String[] lines = msg.split("\n");

        for (String line : lines) {
            line = line.replace("%count%", String.valueOf(staff.size()));

            if (line.contains("%list%")) {
                if (staff.isEmpty()) {
                     sender.sendMessage(ChatUtils.color("&cNo staff online."));
                } else {
                    ComponentBuilder builder = new ComponentBuilder("");
                    String[] parts = line.split("%list%");
                    if (parts.length > 0) builder.append(ChatUtils.color(parts[0]));

                    for (int i = 0; i < staff.size(); i++) {
                        ProxiedPlayer p = staff.get(i);
                        String server = p.getServer().getInfo().getName();
                        String rank = LuckPermsUtils.getGroupName(p);
                        String prefix = LuckPermsUtils.getPrefix(p);

                        TextComponent component = new TextComponent(ChatUtils.color(prefix + p.getName()));
                        component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatUtils.color("&7Server: &e" + server + "\n&7Rank: &f" + rank)).create()));
                        
                        builder.append(component);

                        if (i < staff.size() - 1) {
                            builder.append(ChatUtils.color("&f, "));
                        }
                    }
                    if (parts.length > 1) builder.append(ChatUtils.color(parts[1]));
                    sender.sendMessage(builder.create());
                }
            } else {
                sender.sendMessage(ChatUtils.color(line));
            }
        }
    }
}
