package Golden.Bungee.commands.user;

import Golden.Bungee.Main;
import Golden.Bungee.utils.ChatUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class SocialCommand extends Command {

    private final String messageKey;

    public SocialCommand(String name, String messageKey) {
        super(name, "gbu.command." + name);
        this.messageKey = messageKey;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        String message = Main.getInstance().getConfigManager().getMessage(messageKey);
        sender.sendMessage(ChatUtils.color(message));
    }
}
