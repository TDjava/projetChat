
package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author kerim
 */
public class Client {
    private String address; // adresse IP du serveur
    private int port; //port du serveur
    Socket socket; //le socket pour communiquer avec le serveur
    BufferedReader in; // pour recevoir des messages
    PrintWriter out; // pour envoyer des messages

    public Client(String address, int port) throws IOException {
            this.address = address;
            this.port = port;
            
            InetAddress inetAdr = InetAddress.getByName(address);
            socket = new Socket(inetAdr,port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            
            ClientReceive clientReceive = new ClientReceive(this,socket);
            Thread threadR = new Thread(clientReceive);
            threadR.start();
            ClientSend clientSend = new ClientSend(out);
            Thread threadS = new Thread(clientSend);
            threadS.start();
    
    
    }
    
    
    public void disconnectedServer(){
        
        try {
            this.in.close();
            this.out.close();
            this.socket.close();
            System.exit(0);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    
}
