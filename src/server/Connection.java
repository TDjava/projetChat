
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;



public class Connection implements Runnable {

    Server server; // référence vers l'objet qui l'a créé
    ServerSocket serverSocket; // permet d'accepter de nouvelles connexions

    public Connection(Server server) throws IOException {

        this.server = server;
        this.serverSocket = new ServerSocket(server.getPort());

    }

    @Override
    public void run() {
        try {
            
            while (true) {
                Socket sockNewClient = serverSocket.accept();
                ConnectedClient newClient = new ConnectedClient(server, sockNewClient);
                server.addClient(newClient);
                Thread threadNewClient = new Thread(newClient);
                threadNewClient.start();
                
            }

        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}