// MyAutomessageCommand.java
package pl.mymc.automessage;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.jetbrains.annotations.NotNull;

public class MyAutomessageCommand implements BasicCommand {
    private final My_Automessage plugin;

    public MyAutomessageCommand(My_Automessage plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        PluginDescriptionFile pdf = plugin.getDescription();
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("help")) {
                stack.getSender().sendRichMessage("Dostępne komendy dla myautomessage: help, version");
            } else if (args[0].equalsIgnoreCase("version")) {
                stack.getSender().sendRichMessage("Nazwa pluginu: " + pdf.getName() + ", Autor: " + pdf.getAuthors() + ", Wersja: " + pdf.getVersion());
            }
        } else {
            stack.getSender().sendRichMessage("Testowa wiadomość z komendy");
        }
    }
}

//Wpisz /mam help aby sprawdzic dostępne komendy