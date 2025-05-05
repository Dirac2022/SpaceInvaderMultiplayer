package game;

import java.io.Serializable;

/**
 * Representa un enemigo mediano con resistencia y puntuación moderada.
 * Hereda de Enemy y define un patrón de movimiento específico.
 */
public class MediumEnemy extends Enemy implements Serializable {

    // Dirección inicial del movimiento (derecha)
    private boolean movingRight = true;

    /**
     * Constructor que inicializa posición y puntos.
     * @param x Posición horizontal inicial
     * @param y Posición vertical inicial
     */
    public MediumEnemy(int x, int y) {
        // Llama al constructor de Enemy con 20 puntos (valor del PDF)
        super(x, y, 20);
    }

    /**
     * Implementación del movimiento del enemigo mediano.
     * - Se mueve horizontalmente hasta chocar con los bordes
     * - Cambia dirección al llegar al límite de la pantalla
     */
    @Override
    public void move() {
        if (movingRight) {
            x++;  // Incrementa posición horizontal hacia la derecha
            // Verifica si alcanzó el borde derecho
            if (x >= GameConstants.SCREEN_WIDTH - 2) {
                movingRight = false;  // Cambia dirección
                y++;  // Desciende una fila
            }
        } else {
            x--;  // Decrementa posición horizontal hacia la izquierda
            // Verifica si alcanzó el borde izquierdo
            if (x <= 1) {
                movingRight = true;  // Cambia dirección
                y++;  // Desciende una fila
            }
        }
    }

    /**
     * Representación en cadena para depuración.
     * @return String con posición y tipo
     */
    @Override
    public String toString() {
        return "MediumEnemy[x=" + x + ", y=" + y + "]";
    }
}