package client;

import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author kerim
 */
public class ClientSend implements Runnable{
    PrintWriter out;

    public ClientSend(PrintWriter out) {
        this.out = out;
    }
    

    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("Votre message >> ");
            String m = sc.nextLine();
            out.println(m);
            out.flush();
        }
    }
    
    
    
    
}
