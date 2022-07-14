package de.cubeattack.cuberunner.listeners;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import de.cubeattack.cuberunner.commands.CRCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class ListenerTabComplete implements TabCompleter {
   public List<String> onTabComplete(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
      List<String> tabCompletion = new ArrayList();
      if (!(sender instanceof Player)) {
         return tabCompletion;
      } else {
         Player player = (Player)sender;
         CRCommand command;
         if (args.length == 1) {
            Iterator var8 = CRCommand.getRequiredCommands(player, (CRCommand.CRCommandType)null).iterator();

            while(var8.hasNext()) {
               command = (CRCommand)var8.next();
               if (command.getCommandName().startsWith(args[0].toLowerCase())) {
                  tabCompletion.add(command.getCommandName());
               }
            }

            return tabCompletion;
         } else {
            command = CRCommand.getCommand(args[0].toLowerCase());
            if (command == null) {
               return tabCompletion;
            } else {
               command.complete(tabCompletion, args);
               return tabCompletion;
            }
         }
      }
   }
}
