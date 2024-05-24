package pl.mymc.automessage;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ReloadCommand implements CommandExecutor {
    private My_Automessage plugin;

    public ReloadCommand(My_Automessage plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender.hasPermission("myautomessage.reload")) {
            plugin.reloadConfig();
            sender.sendMessage("Konfiguracja została ponownie załadowana!");
            return true;
        } else {
            sender.sendMessage("Nie masz uprawnień do tej komendy!");
            return false;
        }
    }
}
