package Golden.Bungee.utils;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PlaceholderUtils {

    private static boolean papiPresent = false;

    static {
        if (ProxyServer.getInstance().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            papiPresent = true;
        }
    }

    public static String parse(ProxiedPlayer player, String message) {
        if (!papiPresent) {
            return message;
        }
        return me.rojo8399.placeholderapi.PlaceholderAPI.setPlaceholders(player, message);
    }
}
