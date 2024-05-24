package pl.mymc.automessage;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.configuration.file.FileConfiguration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class My_Automessage extends JavaPlugin {
    private BukkitRunnable autoMessageTask;
    private FileConfiguration config;
    private List<String> messages;
    private int messageIndex = 0;

    @Override
    public void onEnable() {
        getLogger().info("My-AutoMessage włączony.");
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
    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String label, String[] args) {
        if (command.getName().equalsIgnoreCase("myautomessage reload")) {
            if (sender.hasPermission("myautomessage.reload")) {
                reloadPluginConfig();
                sender.sendMessage(NamedTextColor.GREEN + "Konfiguracja została przeładowana.");
            } else {
                sender.sendMessage(NamedTextColor.RED + "Nie masz uprawnień do wykonania tej komendy.");
            }
            return true;
        }
        return false;
    }
    public void reloadPluginConfig() {
        reloadConfig();
    }

    private void startAutoMessageTask() {
        long interval = config.getLong("auto-message.interval-in-ticks");

        autoMessageTask = new BukkitRunnable() {
            @Override
            public void run() {
                String prefix = config.getString("prefix");
                String message = messages.get(messageIndex);

                if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")){
                    if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){

                        message = PlaceholderAPI.setPlaceholders(null, message);
                    } else {
                        getLogger().warning("Nie znalazłem PlaceholderAPI na serwerze.");
                    }
                } else {
                    getLogger().warning("Nie znalazłem  włączonego PlaceholderAPI na serwerze.");
                }
                String fullMessage = prefix + message;
                MiniMessage miniMessage = MiniMessage.miniMessage();
                Component messageComponent = miniMessage.deserialize(fullMessage);
                getServer().broadcast(messageComponent);
                messageIndex = (messageIndex + 1) % messages.size();
            }
        };

        autoMessageTask.runTaskTimer(this, 0L, interval);
    }
}
