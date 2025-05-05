package levels;

import game.*;
import utils.GameConstants;
import java.util.ArrayList;
import java.util.List;

/**
 * Nivel inicial con enemigos básicos en formación estándar.
 */
public class Level1 extends Level {
    public Level1() {
        super(1); // Velocidad lenta
    }

    @Override
    public List<Enemy> loadEnemies() {
        List<Enemy> enemies = new ArrayList<>();
        // Fila superior: 10 enemigos pequeños
        for (int i = 2; i < GameConstants.SCREEN_WIDTH - 2; i += 3) {
            enemies.add(new SmallEnemy(i, 3));
        }
        // Fila media: 8 enemigos medianos
        for (int i = 3; i < GameConstants.SCREEN_WIDTH - 3; i += 4) {
            enemies.add(new MediumEnemy(i, 6));
        }
        // Fila inferior: 6 enemigos grandes
        for (int i = 4; i < GameConstants.SCREEN_WIDTH - 4; i += 5) {
            enemies.add(new LargeEnemy(i, 9));
        }
        return enemies;
    }
}