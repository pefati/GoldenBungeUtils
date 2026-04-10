package Golden.Bungee.commands.user;

import Golden.Bungee.Main;
import Golden.Bungee.utils.ChatUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ReportCommand extends Command {

    public ReportCommand() {
        super("report", "gbu.user.report");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(ChatUtils.color(Main.getInstance().getConfigManager().getMessage("only-players")));
            return;
        }

        if (args.length < 2) {
            sender.sendMessage(ChatUtils.color(Main.getInstance().getConfigManager().getMessage("usage-report")));
            return;
        }

        String targetName = args[0];
        StringBuilder reasonBuilder = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            reasonBuilder.append(args[i]).append(" ");
        }
        String reason = reasonBuilder.toString().trim();

        ComponentBuilder builder = new ComponentBuilder("");
        
        builder.append(ChatUtils.color(Main.getInstance().getConfigManager().getMessage("report-broadcast-header"))).append("\n");
        
        String title = Main.getInstance().getConfigManager().getMessage("report-broadcast-title").replace("%player%", sender.getName());
        builder.append(ChatUtils.color(title)).append("\n");
        
        String reported = Main.getInstance().getConfigManager().getMessage("report-broadcast-target").replace("%target%", targetName);
        builder.append(ChatUtils.color(reported)).append("\n");
        
        String reasonMsg = Main.getInstance().getConfigManager().getMessage("report-broadcast-reason").replace("%reason%", reason);
        builder.append(ChatUtils.color(reasonMsg)).append("\n");
        
        String actionMsg = Main.getInstance().getConfigManager().getMessage("report-broadcast-action");
        TextComponent actionComponent = new TextComponent(ChatUtils.color(actionMsg));
        actionComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/goto " + targetName));
        actionComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatUtils.color("&7Click to teleport to " + targetName)).create()));
        builder.append(actionComponent).append("\n");
        
        builder.append(ChatUtils.color(Main.getInstance().getConfigManager().getMessage("report-broadcast-footer")));

        for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
            if (p.hasPermission("gbu.staff.reports")) {
                p.sendMessage(builder.create());
            }
        }

        sender.sendMessage(ChatUtils.color(Main.getInstance().getConfigManager().getMessage("report-sent")));
    }
}
