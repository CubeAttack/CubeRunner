package de.cubeattack.cuberunner.listeners;

import de.cubeattack.cuberunner.CRPlayer;
import de.cubeattack.cuberunner.CRStats;
import de.cubeattack.cuberunner.game.Arena;
import de.cubeattack.cuberunner.game.GameState;
import de.cubeattack.cuberunner.game.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class ListenerPlayerMove implements Listener {
   @EventHandler
   public void onPlayerMove(PlayerMoveEvent event) throws CRPlayer.PlayerStatsException {
      Player player = event.getPlayer();
      Arena arena = Arena.getArenaFromPlayer(player);
      if (arena != null) {
         User user = arena.getUser(player);
         if (!user.isEliminated()) {
            if (arena.getGameState() == GameState.ACTIVE || arena.getGameState() == GameState.STARTUP) {
               if (!arena.isInsideArena(event.getTo())) {
                  event.setCancelled(true);
               } else if (arena.getGameState() == GameState.ACTIVE) {
                  user.addToDistanceRan(event.getTo().toVector().subtract(event.getFrom().toVector()));
                  double toLocation = event.getTo().getY();
                  double fromLocation = event.getFrom().getY();
                  if (toLocation > arena.getMinPoint().getY() + 10.0D) {
                     user.getCRPlayer().doneChallenge(CRStats.REACH_HEIGHT_10);
                  }

                  if (user.isJumping()) {
                     if (toLocation < fromLocation) {
                        user.setJumping(false);
                     }
                  } else if (toLocation > fromLocation) {
                     user.setJumping(true);
                     user.jump();
                     if (user.getJump() == 50) {
                        user.getCRPlayer().doneChallenge(CRStats.THE_KILLER_BUNNY);
                     }
                  }

               }
            }
         }
      }
   }
}
