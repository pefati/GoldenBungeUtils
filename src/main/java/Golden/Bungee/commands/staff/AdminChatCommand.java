package Golden.Bungee.commands.staff;

import Golden.Bungee.Main;
import Golden.Bungee.listeners.ChatListener;
import Golden.Bungee.utils.ChatUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class AdminChatCommand extends Command {

    public AdminChatCommand() {
        super("adminchat", "gbu.admin.chat", "ac");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(ChatUtils.color(Main.getInstance().getConfigManager().getMessage("only-players")));
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) sender;

        if (args.length > 0) {
            StringBuilder messageBuilder = new StringBuilder();
            for (String arg : args) {
                messageBuilder.append(arg).append(" ");
            }
            ChatListener.sendAdminChat(player, messageBuilder.toString().trim());
        } else {
            Main.getInstance().getChatManager().toggleAdminChat(player.getUniqueId());
            boolean enabled = Main.getInstance().getChatManager().isAdminChatToggled(player.getUniqueId());
            String msg = enabled ? Main.getInstance().getConfigManager().getMessage("adminchat-enabled") : Main.getInstance().getConfigManager().getMessage("adminchat-disabled");
            sender.sendMessage(ChatUtils.color(msg));
        }
    }
}
