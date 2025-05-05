package levels;

import game.*;
import utils.GameConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Segundo nivel con enemigos medianos y pequeños en formación cerrada.
 * Velocidad aumentada en un 50% comparado con el nivel 1.
 */
public class Level2 extends Level {
    public Level2() {
        super(2); // Velocidad media
    }

    @Override
    public List<Enemy> loadEnemies() {
        List<Enemy> enemies = new ArrayList<>();
        
        // 3 filas de enemigos pequeños (formación triangular)
        for (int row = 0; row < 3; row++) {
            for (int i = 2 + row; i < GameConstants.SCREEN_WIDTH - 2 - row; i += 2) {
                enemies.add(new SmallEnemy(i, 3 + row));
            }
        }

        // 2 filas de enemigos medianos
        for (int i = 3; i < GameConstants.SCREEN_WIDTH - 3; i += 3) {
            enemies.add(new MediumEnemy(i, 7));
            enemies.add(new MediumEnemy(i, 9));
        }

        return enemies;
    }
}