package Golden.Bungee;

import Golden.Bungee.commands.admin.GbuCommand;
import Golden.Bungee.commands.admin.StaffTimeCommand;
import Golden.Bungee.commands.staff.*;
import Golden.Bungee.commands.user.HelpopCommand;
import Golden.Bungee.commands.user.MoveCommand;
import Golden.Bungee.commands.user.ReportCommand;
import Golden.Bungee.commands.user.SocialCommand;
import Golden.Bungee.commands.user.StreamCommand;
import Golden.Bungee.listeners.ChatListener;
import Golden.Bungee.listeners.MaintenanceListener;
import Golden.Bungee.listeners.MotdListener;
import Golden.Bungee.listeners.StaffNotificationListener;
import Golden.Bungee.tasks.AnnouncementTask;
import Golden.Bungee.utils.ChatManager;
import Golden.Bungee.utils.ConfigManager;
import Golden.Bungee.utils.LuckPermsUtils;
import Golden.Bungee.utils.SessionManager;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.api.scheduler.ScheduledTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main extends Plugin {

    private static Main instance;
    private ConfigManager configManager;
    private SessionManager sessionManager;
    private ChatManager chatManager;
    private List<ScheduledTask> announcementTasks = new ArrayList<>();

    @Override
    public void onEnable() {
        instance = this;
        
        configManager = new ConfigManager(this);
        
        LuckPermsUtils.init();
        
        sessionManager = new SessionManager();
        getProxy().getPluginManager().registerListener(this, sessionManager);
        
        chatManager = new ChatManager();
        getProxy().getPluginManager().registerListener(this, new ChatListener());
        getProxy().getPluginManager().registerListener(this, new MaintenanceListener());
        getProxy().getPluginManager().registerListener(this, new MotdListener());
        getProxy().getPluginManager().registerListener(this, new StaffNotificationListener());
        
        scheduleAnnouncements();
        
        getProxy().getPluginManager().registerCommand(this, new GbuCommand());
        getProxy().getPluginManager().registerCommand(this, new StaffTimeCommand());

        getProxy().getPluginManager().registerCommand(this, new AlertCommand());
        getProxy().getPluginManager().registerCommand(this, new MaintenanceCommand());
        getProxy().getPluginManager().registerCommand(this, new FindCommand());
        getProxy().getPluginManager().registerCommand(this, new GotoCommand());
        getProxy().getPluginManager().registerCommand(this, new SrvCommand());
        getProxy().getPluginManager().registerCommand(this, new StaffListCommand());
        getProxy().getPluginManager().registerCommand(this, new StaffChatCommand());
        getProxy().getPluginManager().registerCommand(this, new AdminChatCommand());
        getProxy().getPluginManager().registerCommand(this, new NetworkListCommand());
        
        getProxy().getPluginManager().registerCommand(this, new ReportCommand());
        getProxy().getPluginManager().registerCommand(this, new StreamCommand());
        getProxy().getPluginManager().registerCommand(this, new HelpopCommand());
        getProxy().getPluginManager().registerCommand(this, new SocialCommand("discord", "discord"));
        getProxy().getPluginManager().registerCommand(this, new SocialCommand("twitter", "twitter"));
        
        registerMoveCommands();
        
        getLogger().info("GoldenBungeeUtils has been enabled!");
    }

    @Override
    public void onDisable() {
        for (ScheduledTask task : announcementTasks) {
            task.cancel();
        }
        getLogger().info("GoldenBungeeUtils has been disabled!");
    }

    public static Main getInstance() {
        return instance;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public ChatManager getChatManager() {
        return chatManager;
    }

    private void registerMoveCommands() {
        Configuration config = configManager.getConfig();
        Configuration servers = config.getSection("servers");
        if (servers != null) {
            for (String key : servers.getKeys()) {
                String serverName = servers.getString(key);
                getProxy().getPluginManager().registerCommand(this, new MoveCommand(key, serverName));
            }
        }
    }

    public void scheduleAnnouncements() {
        for (ScheduledTask task : announcementTasks) {
            task.cancel();
        }
        announcementTasks.clear();

        Configuration announcements = configManager.getAnnouncements();
        for (String key : announcements.getKeys()) {
            Configuration section = announcements.getSection(key);
            if (section != null) {
                int interval = section.getInt("interval", 15);
                List<String> lines = section.getStringList("lines");
                
                String linkUrl = null;
                String linkText = null;
                
                Configuration linkSection = section.getSection("link");
                if (linkSection != null) {
                    linkUrl = linkSection.getString("url");
                    linkText = linkSection.getString("text");
                }
                
                ScheduledTask task = getProxy().getScheduler().schedule(this, new AnnouncementTask(lines, linkUrl, linkText), interval, interval, TimeUnit.MINUTES);
                announcementTasks.add(task);
            }
        }
    }
}
