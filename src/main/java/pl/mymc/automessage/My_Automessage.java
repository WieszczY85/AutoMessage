package pl.mymc.automessage;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.configuration.file.FileConfiguration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.Component;

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
                String prefix = config.getString("prefix");
                String message = messages.get(messageIndex);
                String fullMessage = prefix + message;
                if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")){
                    if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){

                        fullMessage = PlaceholderAPI.setPlaceholders(null, fullMessage);
                    } else {
                    getLogger().warning("Zapomniałeś dodać PlaceholderAPI na serwer, kretynie?.");
                    }
                }
                MiniMessage miniMessage = MiniMessage.miniMessage();
                Component messageComponent = miniMessage.deserialize(fullMessage);
                getServer().broadcast(messageComponent);
                messageIndex = (messageIndex + 1) % messages.size();
            }
        };

        autoMessageTask.runTaskTimer(this, 0L, interval);
    }
}
