/*
package a7;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServidorTCP {
    private int port;
    private ServidorTCP(int port ) {
        this.port = port;
    }
    private void listen() {
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            while(true) {
                clientSocket = serverSocket.accept();
                ServidorThread servidorThread = new ServidorThread(clientSocket);
                Thread client = new Thread(servidorThread);
                client.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(ServidorTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void main(String[] args) {
        ServidorTCP srv = new ServidorTCP(5000);
        srv.listen();
    }
}
*/