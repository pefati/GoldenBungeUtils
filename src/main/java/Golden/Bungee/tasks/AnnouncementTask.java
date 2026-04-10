package Golden.Bungee.tasks;

import Golden.Bungee.Main;
import Golden.Bungee.utils.ChatUtils;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.List;

public class AnnouncementTask implements Runnable {

    private final List<String> lines;
    private final String linkUrl;
    private final String linkText;

    public AnnouncementTask(List<String> lines, String linkUrl, String linkText) {
        this.lines = lines;
        this.linkUrl = linkUrl;
        this.linkText = linkText;
    }

    @Override
    public void run() {
        if (lines == null || lines.isEmpty()) return;

        for (String line : lines) {
            if (linkUrl != null && !linkUrl.isEmpty() && linkText != null && line.contains("<Link>")) {
                ComponentBuilder builder = new ComponentBuilder("");
                String[] parts = line.split("<Link>");
                
                if (parts.length > 0) {
                    builder.append(ChatUtils.color(parts[0]));
                }
                
                TextComponent linkComponent = new TextComponent(ChatUtils.color(linkText));
                linkComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, linkUrl));
                linkComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatUtils.color(Main.getInstance().getConfigManager().getMessage("announcement.open"))).create()));
                builder.append(linkComponent);
                
                if (parts.length > 1) {
                    builder.append(ChatUtils.color(parts[1]));
                }
                
                ProxyServer.getInstance().broadcast(builder.create());
            } else {
                ProxyServer.getInstance().broadcast(ChatUtils.color(line));
            }
        }
    }
}
