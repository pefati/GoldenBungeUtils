package Golden.Bungee.utils;

import Golden.Bungee.Main;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class ConfigManager {

    private final Main plugin;
    private Configuration config;
    private Configuration messages;
    private Configuration announcements;
    private File configFile;
    private File messagesFile;
    private File announcementsFile;

    public ConfigManager(Main plugin) {
        this.plugin = plugin;
        loadConfig();
    }

    public void loadConfig() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        configFile = new File(plugin.getDataFolder(), "config.yml");
        messagesFile = new File(plugin.getDataFolder(), "messages.yml");
        announcementsFile = new File(plugin.getDataFolder(), "anuncios.yml");

        if (!configFile.exists()) {
            saveResource("config.yml", configFile);
        }
        
        if (!messagesFile.exists()) {
            saveResource("messages.yml", messagesFile);
        }
        
        if (!announcementsFile.exists()) {
            saveResource("anuncios.yml", announcementsFile);
        }

        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
            messages = ConfigurationProvider.getProvider(YamlConfiguration.class).load(messagesFile);
            announcements = ConfigurationProvider.getProvider(YamlConfiguration.class).load(announcementsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void saveResource(String resourceName, File file) {
        try (InputStream in = plugin.getResourceAsStream(resourceName)) {
            if (in != null) {
                Files.copy(in, file.toPath());
            } else {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveConfig() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, configFile);
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(messages, messagesFile);
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(announcements, announcementsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadConfig() {
        loadConfig();
    }

    public Configuration getConfig() {
        return config;
    }
    
    public Configuration getMessages() {
        return messages;
    }
    
    public Configuration getAnnouncements() {
        return announcements;
    }
    
    public String getMessage(String path) {
        Object val = messages.get(path);
        if (val instanceof java.util.List) {
            return ChatUtils.color(String.join("\n", (java.util.List<String>) val));
        }
        return ChatUtils.color(messages.getString(path, "&cMessage not found: " + path));
    }
}
