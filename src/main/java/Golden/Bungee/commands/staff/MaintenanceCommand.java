package Golden.Bungee.commands.staff;

import Golden.Bungee.Main;
import Golden.Bungee.utils.ChatUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.Configuration;

import java.util.List;

public class MaintenanceCommand extends Command {

    public MaintenanceCommand() {
        super("maintenance", "gbu.staff.maintenance");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(ChatUtils.color(Main.getInstance().getConfigManager().getMessage("usage-maintenance")));
            return;
        }

        Configuration config = Main.getInstance().getConfigManager().getConfig();
        String sub = args[0].toLowerCase();

        if (sub.equals("on")) {
            config.set("maintenance.enabled", true);
            Main.getInstance().getConfigManager().saveConfig();
            sender.sendMessage(ChatUtils.color(Main.getInstance().getConfigManager().getMessage("maintenance-enabled")));
        } else if (sub.equals("off")) {
            config.set("maintenance.enabled", false);
            Main.getInstance().getConfigManager().saveConfig();
            sender.sendMessage(ChatUtils.color(Main.getInstance().getConfigManager().getMessage("maintenance-disabled")));
        } else if (sub.equals("add")) {
            if (args.length < 2) {
                sender.sendMessage(ChatUtils.color(Main.getInstance().getConfigManager().getMessage("usage-maintenance-add")));
                return;
            }
            String player = args[1];
            List<String> whitelist = config.getStringList("maintenance.whitelist");
            if (!whitelist.contains(player)) {
                whitelist.add(player);
                config.set("maintenance.whitelist", whitelist);
                Main.getInstance().getConfigManager().saveConfig();
            }
            sender.sendMessage(ChatUtils.color(Main.getInstance().getConfigManager().getMessage("maintenance-added").replace("%player%", player)));
        } else if (sub.equals("remove")) {
            if (args.length < 2) {
                sender.sendMessage(ChatUtils.color(Main.getInstance().getConfigManager().getMessage("usage-maintenance-remove")));
                return;
            }
            String player = args[1];
            List<String> whitelist = config.getStringList("maintenance.whitelist");
            if (whitelist.contains(player)) {
                whitelist.remove(player);
                config.set("maintenance.whitelist", whitelist);
                Main.getInstance().getConfigManager().saveConfig();
            }
            sender.sendMessage(ChatUtils.color(Main.getInstance().getConfigManager().getMessage("maintenance-removed").replace("%player%", player)));
        } else if (sub.equals("list")) {
            List<String> whitelist = config.getStringList("maintenance.whitelist");
            sender.sendMessage(ChatUtils.color(Main.getInstance().getConfigManager().getMessage("maintenance-list-header").replace("%count%", String.valueOf(whitelist.size()))));
            if (whitelist.isEmpty()) {
                sender.sendMessage(ChatUtils.color(Main.getInstance().getConfigManager().getMessage("maintenance-list-empty")));
            } else {
                for (String p : whitelist) {
                    sender.sendMessage(ChatUtils.color("&7- &e" + p));
                }
            }
        } else {
            sender.sendMessage(ChatUtils.color(Main.getInstance().getConfigManager().getMessage("usage-maintenance")));
        }
    }
}
