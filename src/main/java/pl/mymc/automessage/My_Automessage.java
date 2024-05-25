package pl.mymc.automessage;

import com.mojang.brigadier.Command;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.configuration.file.FileConfiguration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.Component;

import java.util.List;

public class My_Automessage extends JavaPlugin implements PluginBootstrap {
    private BukkitRunnable autoMessageTask;
    private FileConfiguration config;
    private List<String> messages;
    private String prefix;
    private int messageIndex = 0;

    @Override
    public void onEnable() {
        try {
            saveDefaultConfig();
            config = getConfig();
            messages = config.getStringList("auto-message.messages");
            prefix = config.getString("auto-message.prefix");
            LifecycleEventManager<Plugin> manager = this.getLifecycleManager();
            manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
                final Commands commands = event.registrar();
                commands.register("myautomessage", "Komenda pluginu My-AutoMessage. Wpisz /mam help aby sprawdzic dostępne komendy", new MyAutomessageCommand(this));
                commands.register("mam", "Komenda pluginu My-AutoMessage. Wpisz /mam help aby sprawdzic dostępne komendy", new MyAutomessageCommand(this));
            });
            startAutoMessageTask();
        } catch (Exception e) {
            getLogger().severe("Wystąpił błąd podczas włączania pluginu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        if (autoMessageTask != null) {
            autoMessageTask.cancel();
        }
    }
    @Override
    public void bootstrap(BootstrapContext context) {
        LifecycleEventManager<BootstrapContext> manager = context.getLifecycleManager();
    }

    private void startAutoMessageTask() {
        try {
            long interval = config.getLong("auto-message.interval-in-ticks");

            autoMessageTask = new BukkitRunnable() {
                @Override
                public void run() {
                    try {
                        String message = prefix + messages.get(messageIndex);
                        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){
                            message = PlaceholderAPI.setPlaceholders(null, message);
                        }
                        MiniMessage miniMessage = MiniMessage.miniMessage();
                        Component messageComponent = miniMessage.deserialize(message);
                        getServer().broadcast(messageComponent);
                        messageIndex = (messageIndex + 1) % messages.size();
                    } catch (Exception e) {
                        getLogger().severe("Wystąpił błąd podczas wysyłania wiadomości: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            };

            autoMessageTask.runTaskTimer(this, 0L, interval);
        } catch (Exception e) {
            getLogger().severe("Wystąpił błąd podczas uruchamiania zadania: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

