package Golden.Bungee.commands.user;

import Golden.Bungee.Main;
import Golden.Bungee.utils.ChatUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class StreamCommand extends Command {

    public StreamCommand() {
        super("stream", "gbu.command.stream", "directo");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(ChatUtils.color(Main.getInstance().getConfigManager().getMessage("usage-stream")));
            return;
        }

        String url = args[0];
        String format = Main.getInstance().getConfigManager().getMessage("stream-broadcast");
        
        String message = format.replace("%player%", sender.getName());
        
        String clickText = Main.getInstance().getConfigManager().getMessage("stream-component");
        
        ComponentBuilder builder = new ComponentBuilder("");
        
        if (message.contains(clickText)) {
            String[] parts = message.split(clickText);
            if (parts.length > 0) {
                builder.append(ChatUtils.color(parts[0]));
            }
            
            TextComponent clickComponent = new TextComponent(ChatUtils.color("&b&n" + clickText));
            clickComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
            builder.append(clickComponent);
            
            if (parts.length > 1) {
                builder.append(ChatUtils.color(parts[1]));
            }
        } else {
            builder.append(ChatUtils.color(message + " "));
            TextComponent clickComponent = new TextComponent(ChatUtils.color(Main.getInstance().getConfigManager().getMessage("stream-click-text")));
            clickComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
            builder.append(clickComponent);
        }
        
        ProxyServer.getInstance().broadcast(builder.create());
    }
}
