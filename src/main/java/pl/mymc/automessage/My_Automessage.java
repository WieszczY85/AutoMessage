package pl.mymc.automessage;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.configuration.file.FileConfiguration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.Component;

import java.util.List;

public final class My_Automessage extends JavaPlugin {
    private BukkitRunnable autoMessageTask;
    private FileConfiguration config;
    private List<String> messages;
    private String prefix;
    private int messageIndex = 0;

    private static My_Automessage plugin;

    public static My_Automessage getPlugin() {
        return plugin;
    }
    @Override
    public void onEnable() {
        plugin = this;
        saveDefaultConfig();
        config = getConfig();
        messages = config.getStringList("auto-message.messages");
        prefix = config.getString("auto-message.prefix");
        getCommand("reload").setExecutor(new ReloadCommand());
        startAutoMessageTask();

    }

    @Override
    public void onDisable() {
        if (autoMessageTask != null) {
            autoMessageTask.cancel();
        }
    }

    private void startAutoMessageTask() {
        long interval = config.getLong("auto-message.interval-in-ticks");

        autoMessageTask = new BukkitRunnable() {
            @Override
            public void run() {
                String message = prefix + messages.get(messageIndex);
                if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){
                    message = PlaceholderAPI.setPlaceholders(null, message);
                }
                MiniMessage miniMessage = MiniMessage.miniMessage();
                Component messageComponent = miniMessage.deserialize(message);
                getServer().broadcast(messageComponent);
                messageIndex = (messageIndex + 1) % messages.size();
            }
        };

        autoMessageTask.runTaskTimer(this, 0L, interval);
    }
}
