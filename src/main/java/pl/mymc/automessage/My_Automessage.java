package pl.mymc.automessage;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.configuration.file.FileConfiguration;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

import java.util.List;

public class My_Automessage extends JavaPlugin {
    private BukkitRunnable autoMessageTask;
    private FileConfiguration config;
    private List<String> messages;
    private int messageIndex = 0;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        config = getConfig();
        messages = config.getStringList("auto-message.messages");
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){
            new MyPlaceholders(this).register();
        }
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
                String message = messages.get(messageIndex);
                getServer().broadcastMessage(message);
                messageIndex = (messageIndex + 1) % messages.size();
            }
        };

        // Uruchomienie zadania zgodnie z interwałem określonym w konfiguracji
        autoMessageTask.runTaskTimer(this, 0L, interval);
    }
}
