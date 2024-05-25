// Plik: MyAutomessageCommand.java
package pl.mymc.automessage;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

public class MyAutomessageCommand implements CommandExecutor {
    private final My_Automessage plugin;

    public MyAutomessageCommand(My_Automessage plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("myautomessage")) {
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("help")) {
                    if (sender instanceof Player) {
                        Player player = (Player) sender;
                        player.sendMessage("To jest komenda pomocy. Dostępne komendy w pluginie to: xxxx, xxxx");
                    }
                } else if (args[0].equalsIgnoreCase("version")) {
                    if (sender instanceof Player) {
                        Player player = (Player) sender;
                        PluginDescriptionFile pdf = plugin.getDescription();
                        player.sendMessage(pdf.getName());
                        player.sendMessage("Autor: " + pdf.getAuthors().get(0));
                        player.sendMessage("Wersja: " + pdf.getVersion());
                    }
                }
                return true;
            }
        }
        return false;
    }
}
