package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kerim
 */
public class ConnectedClient implements Runnable {

    static int idCounter;
    private int id;
    Server server; //référence vers le serveur
    Socket socket; //socket utilisé par le serveur pour communiquer avec le client
    BufferedReader in; //permet d'envoyer des messages au clients
    PrintWriter out; //permet de receptionner les messages du client

    public ConnectedClient(Server server, Socket socket) throws IOException {

        this.server = server;
        this.socket = socket;
        this.id = idCounter++;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream());
        System.out.println("Nouvelle connexion, id = " + id);

    }

    public void sendMessage(String m) {
        this.out.println(m);
        this.out.flush();
    }

    public void closeClient() throws IOException {
        this.in.close();
        this.out.close();
        this.socket.close();
    }

    @Override
    public void run() {

        boolean isActive = true;
        while (isActive) {
            try {
                String message="";
                message = in.readLine();
                if (!message.equals("/quit")) {
                    server.broadcastMessage(message, id);
                    System.out.println("[" + id +"] : " + message);
                } else {
                    System.out.println("déconnexion, id = " + id);
                    server.disconnectedClient(this);
                    
                    isActive = false;

                }
            } catch (SocketException ex) {
                try {
                    System.out.println("déconnexion, id = " + id);
                    server.disconnectedClient(this);
                    isActive = false;
                    this.closeClient();

                } catch (IOException ex1) {
                    Logger.getLogger(ConnectedClient.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } catch (IOException ex) {
                Logger.getLogger(ConnectedClient.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public int getId() {
        return this.id;
    }
    
}
