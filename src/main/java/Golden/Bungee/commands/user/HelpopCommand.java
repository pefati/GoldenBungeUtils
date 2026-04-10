package Golden.Bungee.commands.user;

import Golden.Bungee.Main;
import Golden.Bungee.utils.ChatUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class HelpopCommand extends Command {

    public HelpopCommand() {
        super("helpop", "gbu.user.helpop");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(ChatUtils.color(Main.getInstance().getConfigManager().getMessage("only-players")));
            return;
        }

        if (args.length < 1) {
            sender.sendMessage(ChatUtils.color(Main.getInstance().getConfigManager().getMessage("usage-helpop")));
            return;
        }

        StringBuilder messageBuilder = new StringBuilder();
        for (String arg : args) {
            messageBuilder.append(arg).append(" ");
        }
        String message = messageBuilder.toString().trim();
        ProxiedPlayer player = (ProxiedPlayer) sender;
        
        String format = Main.getInstance().getConfigManager().getMessage("helpop-format").replace("%player%", player.getName()).replace("%message%", message).replace("%server%", player.getServer().getInfo().getName());

        for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
            if (p.hasPermission("gbu.staff.helpop")) {
                p.sendMessage(ChatUtils.color(format));
            }
        }

        sender.sendMessage(ChatUtils.color(Main.getInstance().getConfigManager().getMessage("helpop-sent")));
    }
}
