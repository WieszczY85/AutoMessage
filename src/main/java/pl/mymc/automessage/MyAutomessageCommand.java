// MyAutomessageCommand.java
package pl.mymc.automessage;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.jetbrains.annotations.NotNull;

public class MyAutomessageCommand implements BasicCommand {

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("help")) {
                stack.getSender().sendRichMessage("Dostępne komendy dla myautomessage: help, version");
            } else if (args[0].equalsIgnoreCase("version")) {
                stack.getSender().sendRichMessage("My_Automessage, Autor: WieszczY, Wersja: 1.0.0");
            }
        } else {
            stack.getSender().sendRichMessage("Testowa wiadomość z komendy");
        }
    }
}
