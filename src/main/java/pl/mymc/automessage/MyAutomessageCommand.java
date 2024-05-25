package pl.mymc.automessage;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

public class MyAutomessageCommand {
    public static class HelpCommand implements Command<CommandSender> {
        @Override
        public int run(CommandContext<CommandSender> context) {
            CommandSender sender = context.getSource();
            if (sender instanceof Player) {
                Player player = (Player) sender;
                player.sendMessage("To jest komenda pomocy. Dostępne komendy w pluginie to: xxxx, xxxx");
            }
            return 1;
        }
    }

    public static class VersionCommand implements Command<CommandSender> {
        private final My_Automessage plugin;

        public VersionCommand(My_Automessage plugin) {
            this.plugin = plugin;
        }

        @Override
        public int run(CommandContext<CommandSender> context) {
            CommandSender sender = context.getSource();
            if (sender instanceof Player) {
                Player player = (Player) sender;
                PluginDescriptionFile pdf = plugin.getDescription();
                player.sendMessage("Nazwa pluginu: " + pdf.getName());
                player.sendMessage("Autor: " + pdf.getAuthors().get(0));
                player.sendMessage("Wersja: " + pdf.getVersion());
            }
            return 1;
        }
    }
}
