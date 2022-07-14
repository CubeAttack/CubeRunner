package de.cubeattack.cuberunner.listeners;

import de.cubeattack.cuberunner.CRPlayer;
import de.cubeattack.cuberunner.CRStats;
import de.cubeattack.cuberunner.game.Arena;
import de.cubeattack.cuberunner.game.GameState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class ListenerPlayerDisconnect implements Listener {
   @EventHandler
   public void onPlayerDisconnect(PlayerQuitEvent event) throws CRPlayer.PlayerStatsException {
      Player player = event.getPlayer();
      Arena arena = Arena.getArenaFromPlayer(player);
      if (arena != null) {
         if (arena.getGameState() == GameState.STARTUP) {
            arena.getUser(player).getCRPlayer().doneChallenge(CRStats.THE_RAGE_QUIT);
         }

         arena.removePlayer(player, Arena.LeavingReason.DISCONNECT);
      }
   }
}
