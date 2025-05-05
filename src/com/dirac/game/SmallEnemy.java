package game;

import java.io.Serializable;
import utils.GameConstants;

/**
 * Enemigo pequeño con baja resistencia y 30 puntos al ser destruido.
 * Hereda de Enemy y define su movimiento específico.
 */
public class SmallEnemy extends Enemy implements Serializable {
    private boolean movingRight = true; // Dirección actual de movimiento

    public SmallEnemy(int x, int y) {
        super(x, y, 30); // Llama al constructor de Enemy con 30 puntos
    }

    /**
     * Implementación del movimiento: se desplaza horizontalmente.
     */
    @Override
    public void move() {
        if (movingRight) {
            x++;
            if (x >= GameConstants.SCREEN_WIDTH - 1) movingRight = false;
        } else {
            x--;
            if (x <= 0) movingRight = true;
        }
    }
}