package server;

import java.net.Socket;
import java.io.ObjectInputStream;    // Para recibir objetos del cliente
import java.io.ObjectOutputStream;   // Para enviar objetos al cliente
import java.io.IOException;          // Para excepciones de I/O
import game.GameState;               // Estado del juego
import game.Player;                  // Modelo del jugador

/**
 * Maneja la comunicación con un cliente individual en un hilo separado.
 * Implementa Runnable para ejecutarse concurrentemente.
 */
public class ClientHandler implements Runnable {

    private final Socket socket;          // Socket conectado al cliente
    private final ObjectOutputStream out; // Flujo de salida de objetos
    private final ObjectInputStream in;   // Flujo de entrada de objetos
    private final GameState gameState;    // Referencia al estado del juego
    private final Player player;          // Jugador asociado a este cliente

    /**
     * Constructor que inicializa los flujos de comunicación.
     * @param socket Socket del cliente
     * @param gameState Estado del juego compartido
     * @throws IOException Si hay error al crear los flujos
     */
    public ClientHandler(Socket socket, GameState gameState) throws IOException {
        this.socket = socket;
        this.gameState = gameState;

        // Orden crucial: Primero OutputStream, luego InputStream
        this.out = new ObjectOutputStream(socket.getOutputStream()); // Crea flujo de salida
        this.in = new ObjectInputStream(socket.getInputStream());     // Crea flujo de entrada

        // Crea un nuevo jugador y lo añade al juego
        this.player = new Player();
        synchronized (gameState) { // Bloque sincronizado para modificar estado compartido
            gameState.addPlayer(player);
        }
    }

    /**
     * Método principal del hilo. Escucha acciones del cliente.
     */
    @Override
    public void run() {
        try {
            // Bucle mientras el socket esté abierto
            while (!socket.isClosed()) {
                // Lee la acción enviada por el cliente (bloqueante)
                String action = (String) in.readObject(); 
                
                // Procesa la acción (ej: "MOVE_LEFT", "SHOOT")
                processAction(action);
            }
        } catch (IOException | ClassNotFoundException e) {
            // Maneja desconexiones abruptas
            System.err.println("⚠️ Cliente desconectado: " + e.getMessage());
        } finally {
            // Limpieza al finalizar
            try {
                socket.close(); // Cierra el socket
                Server.removeClient(this); // Elimina de la lista
            } catch (IOException e) {
                System.err.println("Error al cerrar socket: " + e.getMessage());
            }
        }
    }

    /**
     * Procesa una acción recibida del cliente y actualiza el estado del juego.
     * @param action Acción recibida (ej: "MOVE_LEFT")
     */
    private void processAction(String action) {
        // Divide la acción en comando y dirección (ej: "MOVE_LEFT" → ["MOVE", "LEFT"])
        String[] parts = action.split("_");
        String command = parts[0];
        String direction = parts.length > 1 ? parts[1] : "";

        // Bloque sincronizado para modificar el estado del juego
        synchronized (gameState) {
            switch (command) {
                case "MOVE":
                    player.move(direction); // Actualiza posición del jugador
                    break;
                case "SHOOT":
                    // Lógica para disparar (implementar según necesidades)
                    break;
            }
        }
    }

    /**
     * Envía el estado actual del juego al cliente.
     */
    public void sendGameState() {
        try {
            out.writeObject(gameState); // Serializa y envía el objeto
            out.reset(); // Limpia el caché para evitar envío de objetos antiguos
            out.flush(); // Asegura que los datos se envíen inmediatamente
        } catch (IOException e) {
            System.err.println("Error al enviar estado: " + e.getMessage());
        }
    }

    // Getter para el socket (usado en removeClient)
    public Socket getSocket() {
        return socket;
    }
}