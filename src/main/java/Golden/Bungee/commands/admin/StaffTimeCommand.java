package Golden.Bungee.commands.admin;

import Golden.Bungee.Main;
import Golden.Bungee.utils.ChatUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.concurrent.TimeUnit;

public class StaffTimeCommand extends Command {

    public StaffTimeCommand() {
        super("stafftime", "gbu.admin.stafftime");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(ChatUtils.color(Main.getInstance().getConfigManager().getMessage("usage-stafftime")));
            return;
        }

        String targetName = args[0];
        ProxiedPlayer target = Main.getInstance().getProxy().getPlayer(targetName);

        if (target == null) {
            sender.sendMessage(ChatUtils.color(Main.getInstance().getConfigManager().getMessage("player-not-found")));
            return;
        }

        long sessionTime = Main.getInstance().getSessionManager().getSessionTime(target.getUniqueId());
        String formattedTime = formatTime(sessionTime);

        sender.sendMessage(ChatUtils.color(Main.getInstance().getConfigManager().getMessage("stafftime-message").replace("%player%", target.getName()).replace("%time%", formattedTime)));
    }

    private String formatTime(long millis) {
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        long hours = TimeUnit.MILLISECONDS.toHours(millis);

        if (hours > 0) {
            return Main.getInstance().getConfigManager().getMessage("time-format-hours").replace("%hours%", String.valueOf(hours)).replace("%minutes%", String.valueOf(minutes % 60));
        } else if (minutes > 0) {
            return Main.getInstance().getConfigManager().getMessage("time-format-minutes").replace("%minutes%", String.valueOf(minutes)).replace("%seconds%", String.valueOf(seconds % 60));
        } else {
            return Main.getInstance().getConfigManager().getMessage("time-format-seconds").replace("%seconds%", String.valueOf(seconds));
        }
    }
}
