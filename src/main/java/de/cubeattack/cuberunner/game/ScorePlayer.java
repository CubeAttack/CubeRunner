package de.cubeattack.cuberunner.game;

import org.bukkit.OfflinePlayer;

public class ScorePlayer implements Comparable<ScorePlayer> {

    OfflinePlayer player;
    int score;

    public ScorePlayer(OfflinePlayer player, int score) {
        this.player = player;
        this.score = score;
    }

    public int compareTo(ScorePlayer scorePlayer) {
        return Integer.compare(score, scorePlayer.score);
    }

    public String toString(){
        return "Player : " + player.getName() + ", Score : " + score;
    }
}
