package client;


import game.GameState;
import java.io.*;
import java.net.Socket;

/**
 * Cliente que se conecta al servidor y maneja la comunicación.
 */
public class Client {
    private static final String SERVER_IP = "localhost";
    private static final int PORT = GameConstants.SERVER_PORT;

    public static void main(String[] args) {
        try {
            // 1. Establecer conexión con el servidor
            Socket socket = new Socket(SERVER_IP, PORT);
            System.out.println("✅ Conectado al servidor");

            // 2. Inicializar flujos de comunicación
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            // 3. Iniciar interfaz de usuario
            GameUI gameUI = new GameUI(out);
            new Thread(gameUI).start();

            // 4. Bucle principal para recibir actualizaciones del servidor
            while (true) {
                GameState gameState = (GameState) in.readObject();
                gameUI.updateDisplay(gameState);
            }

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("❌ Error en el cliente: " + e.getMessage());
        }
    }
}