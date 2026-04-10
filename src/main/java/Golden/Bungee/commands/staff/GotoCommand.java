package Golden.Bungee.commands.staff;

import Golden.Bungee.Main;
import Golden.Bungee.utils.ChatUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class GotoCommand extends Command {

    public GotoCommand() {
        super("goto", "gbu.staff.goto");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(ChatUtils.color(Main.getInstance().getConfigManager().getMessage("only-players")));
            return;
        }

        if (args.length < 1) {
            sender.sendMessage(ChatUtils.color(Main.getInstance().getConfigManager().getMessage("usage-goto")));
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) sender;
        ProxiedPlayer target = Main.getInstance().getProxy().getPlayer(args[0]);

        if (target == null) {
            sender.sendMessage(ChatUtils.color(Main.getInstance().getConfigManager().getMessage("player-not-found")));
            return;
        }

        if (player.getServer().getInfo().getName().equals(target.getServer().getInfo().getName())) {
            sender.sendMessage(ChatUtils.color(Main.getInstance().getConfigManager().getMessage("already-connected")));
            return;
        }

        player.connect(target.getServer().getInfo());
        String msg = Main.getInstance().getConfigManager().getMessage("goto-message").replace("%player%", target.getName());
        sender.sendMessage(ChatUtils.color(msg));
    }
}
