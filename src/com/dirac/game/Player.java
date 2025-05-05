package game;


import utils.GameConstants;

import java.io.Serializable;
import utils.GameConstants;

public class Player implements Serializable {
    private static int nextId = 1;
    private final int id;
    private int x, y;
    private int score = 0;

    public Player() {
        this.id = nextId++;
    }

    public void move(int direction) {
        x += direction;
        // Limita el movimiento dentro de la pantalla
        x = Math.max(0, Math.min(x, GameConstants.SCREEN_WIDTH - 1));
    }

    public void shoot() {
        // Lógica de disparo (se implementa en el servidor)
    }

    // Getters
    public int getId() { return id; }
    public int getX() { return x; }
    public int getY() { return y; }
}