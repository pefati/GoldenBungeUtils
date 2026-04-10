package Golden.Bungee.utils;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class LuckPermsUtils {

    private static LuckPerms luckPerms;

    public static void init() {
        try {
            luckPerms = LuckPermsProvider.get();
        } catch (IllegalStateException ignored) {
        }
    }

    public static String getPrefix(ProxiedPlayer player) {
        if (luckPerms == null) return "";
        User user = luckPerms.getUserManager().getUser(player.getUniqueId());
        if (user == null) return "";
        String prefix = user.getCachedData().getMetaData().getPrefix();
        return prefix != null ? ChatUtils.color(prefix) : "";
    }

    public static String getSuffix(ProxiedPlayer player) {
        if (luckPerms == null) return "";
        User user = luckPerms.getUserManager().getUser(player.getUniqueId());
        if (user == null) return "";
        String suffix = user.getCachedData().getMetaData().getSuffix();
        return suffix != null ? ChatUtils.color(suffix) : "";
    }
    
    public static String getGroupName(ProxiedPlayer player) {
        if (luckPerms == null) return "";
        User user = luckPerms.getUserManager().getUser(player.getUniqueId());
        if (user == null) return "";
        return user.getPrimaryGroup();
    }
}
