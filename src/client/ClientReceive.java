/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kerim
 */
public class ClientReceive implements Runnable {

    Client client; //référence vers le client qui l'a créé
    BufferedReader in; //permet de recevoir des messages

    public ClientReceive(Client client, Socket s) throws IOException {
        this.client = client;
        this.in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        
        
    }

    @Override
public void run() {
   try {
            boolean isActive = true;
            while (isActive) {

                String message = in.readLine();
                if (message != null) {
                    System.out.println("\nMessage reçu : " + message);

                } else {
                    isActive = false;
                }
            }
            client.disconnectedServer();
        } catch (IOException ex) {
            Logger.getLogger(ClientReceive.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    
}
