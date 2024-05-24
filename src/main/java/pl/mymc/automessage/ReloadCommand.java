package pl.mymc.automessage;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.logging.Level;

public class ReloadCommand implements CommandExecutor {

    public My_Automessage plugin = My_Automessage.getPlugin();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
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