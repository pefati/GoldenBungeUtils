package Golden.Bungee.commands.staff;

import Golden.Bungee.Main;
import Golden.Bungee.utils.ChatUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Command;

import java.util.List;
import java.util.Map;

public class NetworkListCommand extends Command {

    public NetworkListCommand() {
        super("blist", "gbu.user.list");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        int total = ProxyServer.getInstance().getOnlineCount();
        String msg = Main.getInstance().getConfigManager().getMessage("network-list");
        String format = Main.getInstance().getConfigManager().getMessage("network-list-format");

        if (msg == null || msg.isEmpty()) return;

        String[] lines = msg.split("\n");

        for (String line : lines) {
            line = line.replace("%count%", String.valueOf(total));

            if (line.contains("%servers%")) {
                for (Map.Entry<String, ServerInfo> entry : ProxyServer.getInstance().getServers().entrySet()) {
                    ServerInfo server = entry.getValue();
                    int count = server.getPlayers().size();
                    if (count > 0) {
                        String serverLine = format.replace("%server%", server.getName()).replace("%count%", String.valueOf(count));
                        sender.sendMessage(ChatUtils.color(serverLine));
                    }
                }
            } else {
                sender.sendMessage(ChatUtils.color(line));
            }
        }
    }
}
