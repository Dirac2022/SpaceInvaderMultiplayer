package game;

import java.io.Serializable;

/**
 * Representa un enemigo grande con alta resistencia pero baja puntuación.
 * Se mueve más lentamente y en un patrón predecible.
 */
public class LargeEnemy extends Enemy implements Serializable {

    // Contador para controlar la velocidad de movimiento
    private int moveCounter = 0;

    /**
     * Constructor que inicializa posición y puntos.
     * @param x Posición horizontal inicial
     * @param y Posición vertical inicial
     */
    public LargeEnemy(int x, int y) {
        // Llama al constructor de Enemy con 10 puntos (valor del PDF)
        super(x, y, 10);
    }

    /**
     * Implementación del movimiento del enemigo grande.
     * - Se mueve cada dos actualizaciones para simular lentitud
     * - Patrón de movimiento diagonal hacia abajo
     */
    @Override
    public void move() {
        moveCounter++;
        if (moveCounter % 2 == 0) {  // Solo se mueve cada 2 ciclos
            x += (x % 2 == 0) ? 1 : -1;  // Movimiento en zigzag
            y++;  // Desciende gradualmente
        }
    }

    /**
     * Representación en cadena para depuración.
     * @return String con posición y tipo
     */
    @Override
    public String toString() {
        return "LargeEnemy[x=" + x + ", y=" + y + "]";
    }
}
