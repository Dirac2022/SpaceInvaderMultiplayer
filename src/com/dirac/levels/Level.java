package levels;

import game.Enemy;
import java.util.List;

/**
 * Clase abstracta que define la estructura de un nivel.
 * Cada nivel debe implementar loadEnemies() para definir sus enemigos.
 */
public abstract class Level {
    protected int enemySpeed; // Velocidad base de los enemigos
    
    public Level(int enemySpeed) {
        this.enemySpeed = enemySpeed;
    }

    /**
     * Método abstracto para cargar los enemigos del nivel.
     * @return Lista de enemigos
     */
    public abstract List<Enemy> loadEnemies();
}