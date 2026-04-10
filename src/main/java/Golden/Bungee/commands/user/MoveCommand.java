package Golden.Bungee.commands.user;

import Golden.Bungee.Main;
import Golden.Bungee.utils.ChatUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class MoveCommand extends Command {

    private final String serverName;

    public MoveCommand(String commandName, String serverName) {
        super(commandName);
        this.serverName = serverName;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(ChatUtils.color(Main.getInstance().getConfigManager().getMessage("only-players")));
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) sender;
        ServerInfo target = ProxyServer.getInstance().getServerInfo(serverName);

        if (target == null) {
            sender.sendMessage(ChatUtils.color(Main.getInstance().getConfigManager().getMessage("server-not-found").replace("%server%", serverName)));
            return;
        }

        if (player.getServer().getInfo().getName().equals(target.getName())) {
            sender.sendMessage(ChatUtils.color(Main.getInstance().getConfigManager().getMessage("already-connected")));
            return;
        }

        player.connect(target);
        sender.sendMessage(ChatUtils.color(Main.getInstance().getConfigManager().getMessage("connecting").replace("%server%", serverName)));
    }
}
