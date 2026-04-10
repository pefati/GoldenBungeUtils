package Golden.Bungee.commands.staff;

import Golden.Bungee.Main;
import Golden.Bungee.utils.ChatUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;

public class AlertCommand extends Command {

    public AlertCommand() {
        super("alert", "gbu.staff.alert");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(ChatUtils.color(Main.getInstance().getConfigManager().getMessage("usage-alert")));
            return;
        }

        StringBuilder messageBuilder = new StringBuilder();
        for (String arg : args) {
            messageBuilder.append(arg).append(" ");
        }
        String message = messageBuilder.toString().trim();
        String format = Main.getInstance().getConfigManager().getMessage("alert-format");
        
        ProxyServer.getInstance().broadcast(ChatUtils.color(format.replace("%message%", message)));
    }
}
