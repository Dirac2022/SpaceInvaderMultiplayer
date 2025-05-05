package client;

import game.GameState;
import game.Player;
import game.Enemy;
import java.io.ObjectOutputStream;

/**
 * Interfaz gráfica basada en consola que muestra el estado del juego.
 * - Dibuja jugadores, enemigos y proyectiles
 * - Captura entrada del usuario
 */
public class GameUI implements Runnable {
    private final ObjectOutputStream out;
    private GameState lastState;

    public GameUI(ObjectOutputStream out) {
        this.out = out;
    }

    /**
     * Actualiza la pantalla con el último estado del juego.
     * @param state Estado actual del juego
     */
    public void updateDisplay(GameState state) {
        this.lastState = state;
        redraw();
    }

    // Redibuja toda la pantalla
    private void redraw() {
        System.out.print("\033[H\033[2J"); // Limpia la consola (ANSI)
        System.out.flush();

        // Dibuja bordes
        drawHorizontalBorder();
        for (int y = 0; y < GameConstants.SCREEN_HEIGHT; y++) {
            System.out.print("|");
            for (int x = 0; x < GameConstants.SCREEN_WIDTH; x++) {
                char c = ' ';
                // Dibuja jugadores
                for (Player p : lastState.getPlayers()) {
                    if (p.getX() == x && y == GameConstants.SCREEN_HEIGHT - 1) c = 'A';
                }
                // Dibuja enemigos
                for (Enemy e : lastState.getEnemies()) {
                    if (e.getX() == x && e.getY() == y) c = 'M';
                }
                System.out.print(c);
            }
            System.out.println("|");
        }
        drawHorizontalBorder();
    }

    // Dibuja el borde superior/inferior
    private void drawHorizontalBorder() {
        System.out.print("+");
        for (int i = 0; i < GameConstants.SCREEN_WIDTH; i++) System.out.print("-");
        System.out.println("+");
    }

    @Override
    public void run() {
        // Captura entrada del usuario y envía al servidor
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        while (true) {
            String input = scanner.nextLine().toUpperCase();
            try {
                if (input.equals("A")) out.writeObject("MOVE_LEFT");
                else if (input.equals("D")) out.writeObject("MOVE_RIGHT");
                else if (input.equals(" ")) out.writeObject("SHOOT");
            } catch (IOException e) {
                System.err.println("Error al enviar acción: " + e.getMessage());
            }
        }
    }
}