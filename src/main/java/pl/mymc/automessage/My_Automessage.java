package pl.mymc.automessage;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.configuration.file.FileConfiguration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.Component;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.List;

public class My_Automessage extends JavaPlugin {
    private BukkitRunnable autoMessageTask;
    private FileConfiguration config;
    private List<String> messages;
    private String prefix;
    private int messageIndex = 0;
    private CommandDispatcher<CommandSender> commandDispatcher;

    @Override
    public void onEnable() {
        try {
            saveDefaultConfig();
            config = getConfig();
            messages = config.getStringList("auto-message.messages");
            prefix = config.getString("auto-message.prefix");
            commandDispatcher = new CommandDispatcher<>();

            LiteralCommandNode<CommandSender> myautomessageNode = LiteralArgumentBuilder
                    .<CommandSender>literal("myautomessage")
                    .then(LiteralArgumentBuilder.<CommandSender>literal("help")
                            .executes(new MyAutomessageCommand.HelpCommand()))
                    .then(LiteralArgumentBuilder.<CommandSender>literal("version")
                            .executes(new MyAutomessageCommand.VersionCommand(this)))
                    .build();

            commandDispatcher.getRoot().addChild(myautomessageNode);

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
