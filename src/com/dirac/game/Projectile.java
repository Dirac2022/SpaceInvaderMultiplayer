package game;

import java.io.Serializable;

/**
 * Representa un proyectil en el juego con posición y dirección.
 * - Los proyectiles pueden ser del jugador o de enemigos
 * - Se actualizan cada frame para moverse
 */
public class Projectile implements Serializable {
    private int x, y;          // Posición actual
    private final int dy;      // Dirección vertical (-1: arriba, 1: abajo)
    private final boolean isPlayerProjectile; // Tipo de proyectil

    public Projectile(int x, int y, int dy, boolean isPlayerProjectile) {
        this.x = x;
        this.y = y;
        this.dy = dy; // -1 para jugador, 1 para enemigos
        this.isPlayerProjectile = isPlayerProjectile;
    }

    /**
     * Actualiza la posición del proyectil.
     */
    public void update() {
        y += dy; // Mueve el proyectil en su dirección
    }

    // Getters
    public int getX() { return x; }
    public int getY() { return y; }
    public boolean isPlayerProjectile() { return isPlayerProjectile; }
}