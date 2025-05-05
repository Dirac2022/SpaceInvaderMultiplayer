package utils;

import game.Player;
import game.Enemy;
import game.Projectile;
import java.util.List;

/**
 * Utilidad para detectar colisiones entre proyectiles, jugadores y enemigos.
 */
public class CollisionDetector {

    /**
     * Verifica colisiones entre proyectiles y enemigos.
     * @param projectiles Lista de proyectiles
     * @param enemies Lista de enemigos
     * @param player Jugador para actualizar puntuación
     */
    public static void checkCollisions(List<Projectile> projectiles, List<Enemy> enemies, Player player) {
        for (Projectile p : projectiles) {
            if (!p.isPlayerProjectile()) continue; // Solo proyectiles del jugador
            for (Enemy e : enemies) {
                if (Math.abs(p.getX() - e.getX()) <= 1 && p.getY() == e.getY()) {
                    player.addScore(e.getPoints()); // Añade puntos
                    e.markForRemoval(); // Marca enemigo para eliminar
                    projectiles.remove(p); // Elimina proyectil
                    break;
                }
            }
        }
    }
}