package game;

// import java.io.Serializable;
import java.util.Random;

/**
 * Nave nodriza que aparece ocasionalmente y otorga puntos extras.
 * Se mueve horizontalmente en la parte superior.
 */
public class UFO extends Enemy {
    private final Random rand = new Random();
    private int direction; // 1: derecha, -1: izquierda

    public UFO() {
        super(0, 0, rand.nextInt(201) + 100); // Puntos entre 100-300
        this.direction = rand.nextBoolean() ? 1 : -1;
        this.y = 1; // Fila superior
        this.x = direction == 1 ? 0 : GameConstants.SCREEN_WIDTH - 1;
    }

    @Override
    public void move() {
        x += direction;
        if (x < 0 || x >= GameConstants.SCREEN_WIDTH) {
            // Eliminar cuando sale de la pantalla
            markForRemoval();
        }
    }
}