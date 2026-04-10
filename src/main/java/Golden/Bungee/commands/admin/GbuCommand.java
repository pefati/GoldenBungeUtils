package Golden.Bungee.commands.admin;

import Golden.Bungee.Main;
import Golden.Bungee.utils.ChatUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class GbuCommand extends Command {

    public GbuCommand() {
        super("gbu", "gbu.admin.help", "bungeutils", "bu");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("gbu.admin.reload")) {
                sender.sendMessage(ChatUtils.color(Main.getInstance().getConfigManager().getMessage("no-permission")));
                return;
            }
            Main.getInstance().getConfigManager().reloadConfig();
            Main.getInstance().scheduleAnnouncements();
            sender.sendMessage(ChatUtils.color(Main.getInstance().getConfigManager().getMessage("prefix") + Main.getInstance().getConfigManager().getMessage("reload")));
            return;
        }
        
        if (args.length > 0 && args[0].equalsIgnoreCase("help")) {
            sendHelp(sender);
            return;
        }
        
        sendHelp(sender);
    }
    
    private void sendHelp(CommandSender sender) {
        sender.sendMessage(ChatUtils.color(Main.getInstance().getConfigManager().getMessage("help-header")));
        sender.sendMessage(ChatUtils.color(Main.getInstance().getConfigManager().getMessage("help-title")));
        sender.sendMessage(ChatUtils.color(""));
        
        addCommand(sender, "/gbu reload", Main.getInstance().getConfigManager().getMessage("help-desc-reload"));
        addCommand(sender, "/stafftime <player>", Main.getInstance().getConfigManager().getMessage("help-desc-stafftime"));
        addCommand(sender, "/alert <message>", Main.getInstance().getConfigManager().getMessage("help-desc-alert"));
        addCommand(sender, "/maintenance <args>", Main.getInstance().getConfigManager().getMessage("help-desc-maintenance"));
        addCommand(sender, "/find <player>", Main.getInstance().getConfigManager().getMessage("help-desc-find"));
        addCommand(sender, "/goto <player>", Main.getInstance().getConfigManager().getMessage("help-desc-goto"));
        addCommand(sender, "/srv <server>", Main.getInstance().getConfigManager().getMessage("help-desc-srv"));
        addCommand(sender, "/stafflist", Main.getInstance().getConfigManager().getMessage("help-desc-stafflist"));
        addCommand(sender, "/staffchat", Main.getInstance().getConfigManager().getMessage("help-desc-staffchat"));
        addCommand(sender, "/adminchat", Main.getInstance().getConfigManager().getMessage("help-desc-adminchat"));
        addCommand(sender, "/glist", Main.getInstance().getConfigManager().getMessage("help-desc-glist"));
        addCommand(sender, "/report <user> <reason>", Main.getInstance().getConfigManager().getMessage("help-desc-report"));
        addCommand(sender, "/stream <url>", Main.getInstance().getConfigManager().getMessage("help-desc-stream"));
        addCommand(sender, "/helpop <message>", Main.getInstance().getConfigManager().getMessage("help-desc-helpop"));
        addCommand(sender, "/discord", Main.getInstance().getConfigManager().getMessage("help-desc-discord"));
        addCommand(sender, "/twitter", Main.getInstance().getConfigManager().getMessage("help-desc-twitter"));
        
        sender.sendMessage(ChatUtils.color(Main.getInstance().getConfigManager().getMessage("help-footer")));
    }
    
    private void addCommand(CommandSender sender, String command, String description) {
        String format = Main.getInstance().getConfigManager().getMessage("help-command-format")
                .replace("%command%", command)
                .replace("%description%", description);
        TextComponent component = new TextComponent(ChatUtils.color(format));
        component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatUtils.color(Main.getInstance().getConfigManager().getMessage("help-hover"))).create()));
        component.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, command.split(" ")[0]));
        sender.sendMessage(component);
    }
}
