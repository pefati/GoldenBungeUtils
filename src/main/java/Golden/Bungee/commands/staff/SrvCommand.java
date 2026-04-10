package Golden.Bungee.commands.staff;

import Golden.Bungee.Main;
import Golden.Bungee.utils.ChatUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class SrvCommand extends Command {

    public SrvCommand() {
        super("srv", "gbu.staff.srv");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(ChatUtils.color(Main.getInstance().getConfigManager().getMessage("only-players")));
            return;
        }

        if (args.length < 1) {
            sender.sendMessage(ChatUtils.color(Main.getInstance().getConfigManager().getMessage("usage-srv")));
            return;
        }

        String serverName = args[0];
        ServerInfo target = ProxyServer.getInstance().getServerInfo(serverName);

        if (target == null) {
            String msg = Main.getInstance().getConfigManager().getMessage("server-not-found").replace("%server%", serverName);
            sender.sendMessage(ChatUtils.color(msg));
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) sender;
        if (player.getServer().getInfo().getName().equals(target.getName())) {
            sender.sendMessage(ChatUtils.color(Main.getInstance().getConfigManager().getMessage("already-connected")));
            return;
        }

        String msg = Main.getInstance().getConfigManager().getMessage("connecting").replace("%server%", target.getName());
        sender.sendMessage(ChatUtils.color(msg));
        player.connect(target);
    }
}
