package server;

// Importaciones necesarias para redes y concurrencia
import java.net.ServerSocket;    // Para crear el socket del servidor
import java.net.Socket;         // Para aceptar conexiones de clientes
import java.io.IOException;      // Para manejar excepciones de I/O
import java.util.ArrayList;      // Para almacenar clientes conectados
import java.util.Collections;    // Para crear listas seguras en hilos
import game.GameState;           // Estado compartido del juego
import utils.GameConstants;      // Constantes del juego

/**
 * Clase principal del servidor que gestiona conexiones y sincroniza el estado del juego.
 * Utiliza sockets TCP y maneja múltiples clientes mediante hilos.
 */
public class Server {

    // Lista segura para hilos de clientes conectados (evita ConcurrentModificationException)
    private static final ArrayList<ClientHandler> clients = 
        new ArrayList<>(); 

    // Estado del juego compartido entre todos los clientes
    private static final GameState gameState = new GameState();

    /**
     * Método principal que inicia el servidor.
     * @param args Argumentos de línea de comandos (no usados)
     */
    public static void main(String[] args) {
        // Try-with-resources para cerrar automáticamente el ServerSocket
        try (ServerSocket serverSocket = new ServerSocket(GameConstants.SERVER_PORT)) {
            
            // Mensaje de inicio
            System.out.println("🟢 Servidor iniciado en puerto " + GameConstants.SERVER_PORT);

            // Hilo para actualizaciones periódicas del juego
            Thread gameUpdateThread = new Thread(() -> {
                while (true) {
                    try {
                        updateGameState(); // Sincroniza estado con clientes
                        // Pausa para evitar sobrecarga de la CPU
                        Thread.sleep(GameConstants.GAME_UPDATE_DELAY); 
                    } catch (InterruptedException e) {
                        // Restablece el estado de interrupción si es necesario
                        Thread.currentThread().interrupt(); 
                        break;
                    }
                }
            });
            gameUpdateThread.start(); // Inicia el hilo de actualización

            // Bucle principal para aceptar clientes
            while (true) {
                // Espera bloqueante hasta que un cliente se conecte
                Socket clientSocket = serverSocket.accept(); 
                System.out.println("🔗 Cliente conectado: " + clientSocket.getInetAddress());

                // Crea un manejador para el nuevo cliente
                ClientHandler clientHandler = new ClientHandler(clientSocket, gameState);
                
                // Añade el cliente a la lista (sincronizado para seguridad en hilos)
                synchronized (clients) {
                    clients.add(clientHandler);
                }

                // Inicia un hilo independiente para el cliente
                new Thread(clientHandler).start(); 
            }

        } catch (IOException e) {
            // Maneja errores de sockets
            System.err.println("❌ Error en servidor: " + e.getMessage()); 
            e.printStackTrace();
        }
    }

    /**
     * Envía el estado actual del juego a todos los clientes conectados.
     * Sincronizado para evitar condiciones de carrera.
     */
    private static synchronized void updateGameState() {
        // Itera sobre una copia de la lista para evitar modificaciones concurrentes
        ArrayList<ClientHandler> clientsCopy;
        synchronized (clients) {
            clientsCopy = new ArrayList<>(clients);
        }

        // Envía el estado a cada cliente
        for (ClientHandler client : clientsCopy) {
            client.sendGameState();
        }
    }

    /**
     * Elimina un cliente de la lista cuando se desconecta.
     * @param client Cliente a eliminar
     */
    public static synchronized void removeClient(ClientHandler client) {
        clients.remove(client);
        System.out.println("🔴 Cliente desconectado: " + client.getSocket().getInetAddress());
    }
}