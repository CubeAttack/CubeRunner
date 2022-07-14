package de.cubeattack.cuberunner.commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import de.cubeattack.cuberunner.CubeRunner;
import de.cubeattack.cuberunner.Language;
import de.cubeattack.cuberunner.commands.inventories.CRInventoryColor;
import de.cubeattack.cuberunner.commands.inventories.CRInventoryJoin;
import de.cubeattack.cuberunner.commands.inventories.CRInventoryStats;
import de.cubeattack.cuberunner.game.Arena;
import de.cubeattack.cuberunner.game.GameState;
import de.cubeattack.cuberunner.utils.Permissions;
import de.cubeattack.cuberunner.utils.Utils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public enum CRCommand {
   HELP("help", Language.Messages.COMMAND_HELP, "cuberunner.player.help", "/%command% help [category] [page]", CRCommand.CRCommandType.GENERAL) {
      public void execute(CubeRunner plugin, Player player, String[] args, Object... extra) {
         Language local = CubeRunner.get().getLang(player);
         String cmdValue = extra.length > 0 ? (extra[0] instanceof String ? (String)extra[0] : "cr") : "cr";
         String header = Utils.color("&8&m" + StringUtils.repeat(" ", 15) + "&r&8| &5CubeRunner &d" + local.get(Language.Messages.KEYWORD_HELP) + "&8&m|" + StringUtils.repeat(" ", 35));
         if (args.length == 1) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', header));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5/" + cmdValue + " help general &8- " + local.get(Language.Messages.HELP_DESCRIPTION_GENERALL)));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5/" + cmdValue + " help game &8- " + local.get(Language.Messages.HELP_DESCRIPTION_GAME)));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5/" + cmdValue + " help arena &8- " + local.get(Language.Messages.HELP_DESCRIPTION_ARENA)));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5/" + cmdValue + " help admin &8- " + local.get(Language.Messages.HELP_DESCRIPTION_ADMIN)));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5/" + cmdValue + " help all &8- " + local.get(Language.Messages.HELP_DESCRIPTION_ALL)));
            player.sendMessage("\n");
         } else {
            int pageNumber = 1;
            CRCommand.CRCommandType commandType = null;

            List requestedCommands;
            try {
               pageNumber = Integer.parseInt(args[1]);
               if (pageNumber < 1) {
                  pageNumber = 1;
               }

               requestedCommands = CRCommand.getRequiredCommands(player, commandType);
               if ((double)pageNumber > Math.ceil((double)requestedCommands.size() / 3.0D)) {
                  pageNumber = (int)Math.ceil((double)requestedCommands.size() / 3.0D);
               }
            } catch (NumberFormatException var15) {
               String var12;
               switch((var12 = args[1].toLowerCase()).hashCode()) {
               case -80148248:
                  if (var12.equals("general")) {
                     commandType = CRCommand.CRCommandType.GENERAL;
                  }
                  break;
               case 3165170:
                  if (var12.equals("game")) {
                     commandType = CRCommand.CRCommandType.GAME;
                  }
                  break;
               case 92668751:
                  if (var12.equals("admin")) {
                     commandType = CRCommand.CRCommandType.ADMIN;
                  }
                  break;
               case 93078279:
                  if (var12.equals("arena")) {
                     commandType = CRCommand.CRCommandType.ARENA;
                  }
               }

               requestedCommands = CRCommand.getRequiredCommands(player, commandType);
               if (args.length > 2) {
                  try {
                     pageNumber = Integer.parseInt(args[2]);
                     if (pageNumber < 1) {
                        pageNumber = 1;
                     }

                     if ((double)pageNumber > Math.ceil((double)requestedCommands.size() / 3.0D)) {
                        pageNumber = (int)Math.ceil((double)requestedCommands.size() / 3.0D);
                     }
                  } catch (NumberFormatException var14) {
                  }
               }
            }

            if (requestedCommands.size() == 0) {
               pageNumber = 0;
            }

            player.sendMessage(ChatColor.translateAlternateColorCodes('&', header));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5" + local.get(Language.Messages.KEYWORD_HELP_CATEGORY) + ": &7" + (commandType == null ? "ALL" : commandType.toString()) + ", &5" + local.get(Language.Messages.KEYWORD_HELP_PAGE) + ": &7" + pageNumber + "&8/&7" + (int)Math.ceil((double)requestedCommands.size() / 3.0D)));
            if (pageNumber == 0) {
               local.sendMsg(player, local.get(Language.Messages.HELP_ERROR_PERMISSION));
            } else {
               for(int i = 3 * (pageNumber - 1); i < requestedCommands.size() && i < 3 * (pageNumber - 1) + 3; ++i) {
                  player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5" + ((CRCommand)requestedCommands.get(i)).getUsage().replace("%command%", cmdValue)));
                  player.sendMessage(ChatColor.translateAlternateColorCodes('&', " &8- &7" + local.get(((CRCommand)requestedCommands.get(i)).getDescription())));
               }

               player.sendMessage("\n");
            }
         }
      }

      public void complete(List<String> tabCompletion, String[] args) {
         if (args.length == 2) {
            CRCommand.CRCommandType[] var6;
            int var5 = (var6 = CRCommand.CRCommandType.values()).length;

            for(int var4 = 0; var4 < var5; ++var4) {
               CRCommand.CRCommandType category = var6[var4];
               if (category.name().toLowerCase().startsWith(args[1].toLowerCase())) {
                  tabCompletion.add(category.name().toLowerCase());
               }
            }
         }

      }
   },
   LANGUAGE("lang", Language.Messages.COMMAND_LANGUAGE, "cuberunner.player.language", "/%command% language <language>", CRCommand.CRCommandType.GENERAL) {
      public void execute(CubeRunner plugin, Player player, String[] args, Object... extra) {
         Language local = CubeRunner.get().getLang(player);
         if (args.length != 1) {
            Language languagex = Language.getLanguage(args[1]);
            if (languagex == null) {
               String cmdValue = extra.length > 0 ? (extra[0] instanceof String ? (String)extra[0] : "cr") : "cr";
               local.sendMsg(player, local.get(Language.Messages.LANGUAGE_NOT_FOUND).replace("%cmd%", cmdValue));
            } else {
               CubeRunner.get().getCRPlayer(player).setLanguage(languagex);
               languagex.sendMsg(player, languagex.get(Language.Messages.LANGUAGE_CHANGED));
            }
         } else {
            local.sendMsg(player, local.get(Language.Messages.LANGUAGE_LIST));
            Iterator var7 = Language.getLanguages().entrySet().iterator();

            while(var7.hasNext()) {
               Entry<String, Language> language = (Entry)var7.next();
               player.sendMessage("- " + ((Language)language.getValue()).getLanguageName());
            }

         }
      }

      public void complete(List<String> tabCompletion, String[] args) {
         if (args.length == 2) {
            Iterator var4 = Language.getLanguages().entrySet().iterator();

            while(var4.hasNext()) {
               Entry<String, Language> lang = (Entry)var4.next();
               if (((Language)lang.getValue()).getLanguageName().toLowerCase().startsWith(args[1].toLowerCase())) {
                  tabCompletion.add(((Language)lang.getValue()).getLanguageName());
               }
            }
         }

      }
   },
   STATS("stats", Language.Messages.COMMAND_STATS, "cuberunner.player.stats", "/%command% stats", CRCommand.CRCommandType.GENERAL) {
      public void execute(CubeRunner plugin, Player player, String[] args, Object... extra) {
         new CRInventoryStats(plugin.getCRPlayer(player));
      }

      public void complete(List<String> tabCompletion, String[] args) {
      }
   },
   LIST("list", Language.Messages.COMMAND_LIST, "cuberunner.player.list", "/%command% list", CRCommand.CRCommandType.GAME) {
      public void execute(CubeRunner plugin, Player player, String[] args, Object... extra) {
         JOIN.execute(plugin, player, new String[0]);
      }

      public void complete(List<String> tabCompletion, String[] args) {
      }
   },
   INFO("info", Language.Messages.COMMAND_INFO, "cuberunner.player.info", "/%command% info <arena>", CRCommand.CRCommandType.GAME) {
      public void execute(CubeRunner plugin, Player player, String[] args, Object... extra) {
         Language local = CubeRunner.get().getLang(player);
         Arena arena = args.length > 1 ? Arena.getArena(args[1]) : null;
         if (arena == null) {
            local.sendMsg(player, local.get(Language.Messages.ERROR_MISSING_ARENA));
         } else {
            String cmdValue = extra.length > 0 ? (extra[0] instanceof String ? (String)extra[0] : "cr") : "cr";
            if (args.length == 1) {
               local.sendMsg(player, local.get(Language.Messages.ERROR_MISSING_ARENA));
               local.sendMsg(player, local.get(Language.Messages.INFO_TIP).replace("%cmd%", cmdValue));
            } else {
               arena = Arena.getArena(args[1]);
               if (arena == null) {
                  local.sendMsg(player, local.get(Language.Messages.ERROR_MISSING_ARENA).replace("%arena%", args[1]));
                  local.sendMsg(player, local.get(Language.Messages.INFO_TIP).replace("%cmd%", cmdValue));
               } else {
                  arena.displayInformation(player);
               }
            }
         }
      }

      public void complete(List<String> tabCompletion, String[] args) {
         if (args.length == 2) {
            Iterator var4 = Arena.getArenas().iterator();

            while(var4.hasNext()) {
               Arena arena = (Arena)var4.next();
               if (arena.getName().startsWith(args[1].toLowerCase())) {
                  tabCompletion.add(arena.getName());
               }
            }
         }

      }
   },
   JOIN("join", Language.Messages.COMMAND_JOIN, "cuberunner.player.play.join", "/%command% join [arena]", CRCommand.CRCommandType.GAME) {
      public void execute(CubeRunner plugin, Player player, String[] args, Object... extra) {
         Language local = CubeRunner.get().getLang(player);
         Arena arena = Arena.getArenaFromPlayer(player);
         if (arena != null) {
            local.sendMsg(player, local.get(Language.Messages.ERROR_ALREADY_IN_GAME));
         } else {
            arena = args.length > 1 ? Arena.getArena(args[1]) : null;
            if (arena == null) {
               new CRInventoryJoin(CubeRunner.get().getCRPlayer(player), 1);
            } else {
               boolean teleport = extra.length > 0 ? (extra[0] instanceof Boolean ? (Boolean)extra[0] : true) : false;
               arena.addPlayer(player, teleport);
            }
         }
      }

      public void complete(List<String> tabCompletion, String[] args) {
         if (args.length == 2) {
            Iterator var4 = Arena.getArenas().iterator();

            while(var4.hasNext()) {
               Arena arena = (Arena)var4.next();
               if (arena.getName().toLowerCase().startsWith(args[1].toLowerCase())) {
                  tabCompletion.add(arena.getName());
               }
            }
         }

      }
   },
   QUIT("quit", Language.Messages.COMMAND_QUIT, "cuberunner.player.play.quit", "/%command% quit", CRCommand.CRCommandType.GAME) {
      public void execute(CubeRunner plugin, Player player, String[] args, Object... extra) {
         Language local = CubeRunner.get().getLang(player);
         Arena arena = Arena.getArenaFromPlayer(player);
         if (arena == null) {
            local.sendMsg(player, local.get(Language.Messages.ERROR_NOT_IN_GAME));
         } else {
            arena.removePlayer(player, Arena.LeavingReason.COMMAND);
         }
      }

      public void complete(List<String> tabCompletion, String[] args) {
      }
   },
   START("start", Language.Messages.COMMAND_START, "cuberunner.player.play.start", "/%command% start", CRCommand.CRCommandType.GAME) {
      public void execute(CubeRunner plugin, Player player, String[] args, Object... extra) {
         Language local = CubeRunner.get().getLang(player);
         Arena arena = Arena.getArenaFromPlayer(player);
         if (arena == null) {
            local.sendMsg(player, local.get(Language.Messages.ERROR_NOT_IN_GAME));
         } else if (arena.getGameState() != GameState.READY) {
            local.sendMsg(player, local.get(Language.Messages.START_NOT_READY));
         } else {
            arena.initiateGame(player);
         }
      }

      public void complete(List<String> tabCompletion, String[] args) {
      }
   },
   NEW("new", Language.Messages.COMMAND_NEW, "cuberunner.admin.edit.new", "/%command% new <arenaName>", CRCommand.CRCommandType.ARENA) {
      public void execute(CubeRunner plugin, Player player, String[] args, Object... extra) {
         Language local = CubeRunner.get().getLang(player);
         if (args.length <= 1) {
            local.sendMsg(player, local.get(Language.Messages.EDIT_CREATE_NONAME));
         } else if (Arena.getArena(args[1]) != null) {
            local.sendMsg(player, local.get(Language.Messages.EDIT_NEW_EXISTS).replace("%arena%", args[1]));
         } else if (args[1].length() > 12) {
            local.sendMsg(player, local.get(Language.Messages.EDIT_NEW_LONG_NAME));
         } else {
            new Arena(args[1], player);
            local.sendMsg(player, local.get(Language.Messages.EDIT_NEW_SUCCESS).replace("%arena%", args[1]));
         }
      }

      public void complete(List<String> tabCompletion, String[] args) {
      }
   },
   DELETE("delete", Language.Messages.COMMAND_DELETE, "cuberunner.admin.edit.delete", "/%command% delete <arenaName>", CRCommand.CRCommandType.ARENA) {
      public void execute(CubeRunner plugin, Player player, String[] args, Object... extra) {
         Language local = CubeRunner.get().getLang(player);
         Arena arena = args.length > 1 ? Arena.getArena(args[1]) : null;
         if (arena == null) {
            local.sendMsg(player, local.get(Language.Messages.ERROR_MISSING_ARENA));
         } else {
            arena.delete(player);
         }
      }

      public void complete(List<String> tabCompletion, String[] args) {
         if (args.length == 2) {
            Iterator var4 = Arena.getArenas().iterator();

            while(var4.hasNext()) {
               Arena arena = (Arena)var4.next();
               if (arena.getName().toLowerCase().startsWith(args[1].toLowerCase())) {
                  tabCompletion.add(arena.getName());
               }
            }
         }

      }
   },
   SETZONE("setzone", Language.Messages.COMMAND_SETZONE, "cuberunner.admin.edit.zone", "/%command% setzone <arenaName>", CRCommand.CRCommandType.ARENA) {
      public void execute(CubeRunner plugin, Player player, String[] args, Object... extra) {
         Language local = CubeRunner.get().getLang(player);
         Arena arena = args.length > 1 ? Arena.getArena(args[1]) : null;
         if (arena == null) {
            local.sendMsg(player, local.get(Language.Messages.ERROR_MISSING_ARENA));
         } else {
            try {
               arena.setArena(player);
            }catch (Exception ex){
                  ex.printStackTrace();
            }
         }
      }

      public void complete(List<String> tabCompletion, String[] args) {
         if (args.length == 2) {
            Iterator var4 = Arena.getArenas().iterator();

            while(var4.hasNext()) {
               Arena arena = (Arena)var4.next();
               if (arena.getName().toLowerCase().startsWith(args[1].toLowerCase())) {
                  tabCompletion.add(arena.getName());
               }
            }
         }

      }
   },
   SETLOBBY("setlobby", Language.Messages.COMMAND_SETLOBBY, "cuberunner.admin.edit.lobby", "/%command% setlobby <arenaName>", CRCommand.CRCommandType.ARENA) {
      public void execute(CubeRunner plugin, Player player, String[] args, Object... extra) {
         Language local = CubeRunner.get().getLang(player);
         Arena arena = args.length > 1 ? Arena.getArena(args[1]) : null;
         if (arena == null) {
            local.sendMsg(player, local.get(Language.Messages.ERROR_MISSING_ARENA));
         } else {
            arena.setLobby(player);
         }
      }

      public void complete(List<String> tabCompletion, String[] args) {
         if (args.length == 2) {
            Iterator var4 = Arena.getArenas().iterator();

            while(var4.hasNext()) {
               Arena arena = (Arena)var4.next();
               if (arena.getName().toLowerCase().startsWith(args[1].toLowerCase())) {
                  tabCompletion.add(arena.getName());
               }
            }
         }

      }
   },
   SETSTARTPOINT("setstartpoint", Language.Messages.COMMAND_SETSTARTPOINT, "cuberunner.admin.edit.startpoint", "/%command% setstartpoint <arenaName>", CRCommand.CRCommandType.ARENA) {
      public void execute(CubeRunner plugin, Player player, String[] args, Object... extra) {
         Language local = CubeRunner.get().getLang(player);
         Arena arena = args.length > 1 ? Arena.getArena(args[1]) : null;
         if (arena == null) {
            local.sendMsg(player, local.get(Language.Messages.ERROR_MISSING_ARENA));
         } else {
            arena.setStartPoint(player);
         }
      }

      public void complete(List<String> tabCompletion, String[] args) {
         if (args.length == 2) {
            Iterator var4 = Arena.getArenas().iterator();

            while(var4.hasNext()) {
               Arena arena = (Arena)var4.next();
               if (arena.getName().toLowerCase().startsWith(args[1].toLowerCase())) {
                  tabCompletion.add(arena.getName());
               }
            }
         }

      }
   },
   SETMINPLAYER("setminplayer", Language.Messages.COMMAND_SETMINPLAYER, "cuberunner.admin.edit.amountplayer.minplayer", "/%command% setminplayer <arenaName> <amount>", CRCommand.CRCommandType.ARENA) {
      public void execute(CubeRunner plugin, Player player, String[] args, Object... extra) {
         Language local = CubeRunner.get().getLang(player);
         Arena arena = args.length > 1 ? Arena.getArena(args[1]) : null;
         if (arena == null) {
            local.sendMsg(player, local.get(Language.Messages.ERROR_MISSING_ARENA));
         } else {
            if (args.length < 3) {
               local.sendMsg(player, local.get(Language.Messages.EDIT_PLAYERS_MISSING));
            }

            try {
               arena.setMinPlayer(Integer.parseInt(args[2]), player);
            } catch (NumberFormatException var8) {
               local.sendMsg(player, local.get(Language.Messages.EDIT_PLAYERS_ERROR).replace("%error%", local.get(Language.Messages.EDIT_PLAYERS_NAN)));
            }
         }
      }

      public void complete(List<String> tabCompletion, String[] args) {
         if (args.length == 2) {
            Iterator var4 = Arena.getArenas().iterator();

            while(var4.hasNext()) {
               Arena arena = (Arena)var4.next();
               if (arena.getName().toLowerCase().startsWith(args[1].toLowerCase())) {
                  tabCompletion.add(arena.getName());
               }
            }
         }

      }
   },
   SETMAXPLAYER("setmaxplayer", Language.Messages.COMMAND_SETMAXPLAYER, "cuberunner.admin.edit.amountplayer.maxplayer", "/%command% setmaxplayer <arenaName> <amount>", CRCommand.CRCommandType.ARENA) {
      public void execute(CubeRunner plugin, Player player, String[] args, Object... extra) {
         Language local = CubeRunner.get().getLang(player);
         Arena arena = args.length > 1 ? Arena.getArena(args[1]) : null;
         if (arena == null) {
            local.sendMsg(player, local.get(Language.Messages.ERROR_MISSING_ARENA));
         } else {
            if (args.length < 3) {
               local.sendMsg(player, local.get(Language.Messages.EDIT_PLAYERS_MISSING));
            }

            try {
               arena.setMaxPlayer(Integer.parseInt(args[2]), player);
            } catch (NumberFormatException var8) {
               local.sendMsg(player, local.get(Language.Messages.EDIT_PLAYERS_ERROR).replace("%error%", local.get(Language.Messages.EDIT_PLAYERS_NAN)));
            }
         }
      }

      public void complete(List<String> tabCompletion, String[] args) {
         if (args.length == 2) {
            Iterator var4 = Arena.getArenas().iterator();

            while(var4.hasNext()) {
               Arena arena = (Arena)var4.next();
               if (arena.getName().toLowerCase().startsWith(args[1].toLowerCase())) {
                  tabCompletion.add(arena.getName());
               }
            }
         }

      }
   },
   SETCOLOR("setcolor", Language.Messages.COMMAND_SETCOLOR, "cuberunner.admin.edit.color", "/%command% setcolor <arenaName>", CRCommand.CRCommandType.ARENA) {
      public void execute(CubeRunner plugin, Player player, String[] args, Object... extra) {
         Language local = CubeRunner.get().getLang(player);
         Arena arena = args.length > 1 ? Arena.getArena(args[1]) : null;
         if (arena == null) {
            local.sendMsg(player, local.get(Language.Messages.ERROR_MISSING_ARENA));
         } else if (arena.getGameState() != GameState.ACTIVE && arena.getGameState() != GameState.ENDING) {
            new CRInventoryColor(plugin.getCRPlayer(player), arena);
         } else {
            local.sendMsg(player, local.get(Language.Messages.EDIT_COLOR_ERROR));
         }
      }

      public void complete(List<String> tabCompletion, String[] args) {
         if (args.length == 2) {
            Iterator var4 = Arena.getArenas().iterator();

            while(var4.hasNext()) {
               Arena arena = (Arena)var4.next();
               if (arena.getName().toLowerCase().startsWith(args[1].toLowerCase())) {
                  tabCompletion.add(arena.getName());
               }
            }
         }

      }
   },
   RELOAD("reload", Language.Messages.COMMAND_RELOAD, "cuberunner.admin.reload", "/%command% reload", CRCommand.CRCommandType.ADMIN) {
      public void execute(CubeRunner plugin, Player player, String[] args, Object... extra) {
         CubeRunner.get().reload();
         Language local = CubeRunner.get().getLang(player);
         local.sendMsg(player, local.get(Language.Messages.ADMIN_RELOAD));
      }

      public void complete(List<String> tabCompletion, String[] args) {
      }
   };

   private final String commandName;
   private final Language.Messages description;
   private final String permission;
   private final String usage;
   private final CRCommand.CRCommandType type;

   public abstract void execute(CubeRunner var1, Player var2, String[] var3, Object... var4);

   public abstract void complete(List<String> var1, String[] var2);

   private CRCommand(String commandName, Language.Messages description, String permission, String usage, CRCommand.CRCommandType type) {
      this.commandName = commandName;
      this.description = description;
      this.permission = permission;
      this.usage = usage;
      this.type = type;
   }

   public String getCommandName() {
      return this.commandName;
   }

   public Language.Messages getDescription() {
      return this.description;
   }

   public String getPermission() {
      return this.permission;
   }

   public String getUsage() {
      return this.usage;
   }

   public static CRCommand getCommand(String argument) {
      CRCommand[] var4;
      int var3 = (var4 = values()).length;

      for(int var2 = 0; var2 < var3; ++var2) {
         CRCommand command = var4[var2];
         if (command.commandName.equalsIgnoreCase(argument)) {
            return command;
         }
      }

      return null;
   }

   public static List<CRCommand> getRequiredCommands(Player player, CRCommand.CRCommandType commandType) {
      List<CRCommand> requestedCommands = new ArrayList();
      CRCommand[] var6;
      int var5 = (var6 = values()).length;

      for(int var4 = 0; var4 < var5; ++var4) {
         CRCommand command = var6[var4];
         if ((command.type == commandType || commandType == null) && Permissions.hasPermission(command.permission, player, false)) {
            requestedCommands.add(command);
         }
      }

      return requestedCommands;
   }

   // $FF: synthetic method
   CRCommand(String var3, Language.Messages var4, String var5, String var6, CRCommand.CRCommandType var7, CRCommand var8) {
      this(var3, var4, var5, var6, var7);
   }

   public static enum CRCommandType {
      GENERAL,
      GAME,
      ARENA,
      ADMIN;
   }
}
