package de.cubeattack.cuberunner.listeners;

import de.cubeattack.cuberunner.CRPlayer;
import de.cubeattack.cuberunner.CRStats;
import de.cubeattack.cuberunner.CubeRunner;
import de.cubeattack.cuberunner.game.Arena;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
public class ListenerPlayerDeath implements Listener {

    @EventHandler
    public static void onPlayerDeath(PlayerDeathEvent event) throws CRPlayer.PlayerStatsException {
        Player player = event.getEntity();
        Arena arena = Arena.getArenaFromPlayer(player);
        if (arena != null) {
            if(player.getKiller() != null){
                arena.eliminateUser(arena.getUser(player), Arena.LeavingReason.KILLED);
                CubeRunner.get().getCRPlayer(player.getKiller()).increment(CRStats.KILLS, true);
            }
        }
    }
}
