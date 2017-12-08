package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Server {

    private int port;
    private List<ConnectedClient> clients;

    public Server(int port) throws IOException {
        this.port = port;
        this.clients = new ArrayList<>();
        Thread threadConnection = new Thread(new Connection(this));
        threadConnection.start();

    }

    public int getPort() {
        return this.port;
    }

    public void addClient(ConnectedClient newClient) {
        for (ConnectedClient client : clients) {
            client.sendMessage("Le client " + newClient.getId() + " vient de se connecter");

        }
        this.clients.add(newClient);
    }

    public void broadcastMessage(String message, int id) {
        for (ConnectedClient client : clients) {
            if (client.getId() != id) {
                client.sendMessage("Message de " + id + " : " + message);
            }
        }
    }

    public void disconnectedClient(ConnectedClient discClient) throws IOException {
        
        clients.remove(discClient);
        discClient.closeClient();
        
        for (ConnectedClient client : clients) {
            
            client.sendMessage("Le client " + discClient.getId() + " nous a quitté"); // message vers tout le monde sauf au client  qui s'est déconnecté
        }
        
        
    }
}