package game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameState implements Serializable {
    private List<Player> players = new ArrayList<>();
    private List<Enemy> enemies = new ArrayList<>();
    private int level = 1;

    // Añade un jugador con posición inicial única
    public synchronized void addPlayer(Player player) {
        // Posición inicial distribuida horizontalmente
        player.setX((GameConstants.SCREEN_WIDTH / (players.size() + 1)) * (players.size() + 1));
        player.setY(GameConstants.SCREEN_HEIGHT - 2); // Parte inferior
        players.add(player);
    }

    // Actualiza un jugador específico por ID
    public synchronized void updatePlayer(int playerId, String action) {
        for (Player p : players) {
            if (p.getId() == playerId) {
                switch (action) {
                    case "MOVE_LEFT": p.move(-1); break;
                    case "MOVE_RIGHT": p.move(1); break;
                    case "SHOOT": p.shoot(); break;
                }
            }
        }
    }

    // Getters
    public synchronized List<Player> getPlayers() { return new ArrayList<>(players); }
    public synchronized List<Enemy> getEnemies() { return new ArrayList<>(enemies); }
}