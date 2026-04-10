package Golden.Bungee.commands.staff;

import Golden.Bungee.Main;
import Golden.Bungee.utils.ChatUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class FindCommand extends Command {

    public FindCommand() {
        super("find", "gbu.staff.find");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(ChatUtils.color(Main.getInstance().getConfigManager().getMessage("usage-find")));
            return;
        }

        ProxiedPlayer target = Main.getInstance().getProxy().getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(ChatUtils.color(Main.getInstance().getConfigManager().getMessage("player-not-found")));
            return;
        }

        String server = target.getServer().getInfo().getName();
        String msg = Main.getInstance().getConfigManager().getMessage("find-message").replace("%player%", target.getName()).replace("%server%", server);
        sender.sendMessage(ChatUtils.color(msg));
    }
}
