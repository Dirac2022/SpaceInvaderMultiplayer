package game;

import java.io.Serializable;

/**
 * Clase base para todos los tipos de enemigos.
 */
public abstract class Enemy implements Serializable {
    protected int x, y;
    protected int points;

    public abstract void move();
    public int getPoints() { return points; }
}