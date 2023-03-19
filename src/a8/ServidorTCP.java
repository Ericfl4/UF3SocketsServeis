package a8;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServidorTCP {
    private int port;
    private SecretNum ns;
    private Tauler t;
    private ServidorTCP(int port, Tauler t) {
        this.port = port;
        ns = new SecretNum(100);
        this.t = t;
    }
    private void listen() {
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            while(true) { //esperar connexió del client i llançar thread
                clientSocket = serverSocket.accept();
                //Llançar Thread per establir la comunicació
                //sumem 1 al numero de jugadors
                t.addNumPlayers();
                ThreadServidorTCP FilServidor = new ThreadServidorTCP(clientSocket, ns, t);
                Thread client = new Thread(FilServidor);
                client.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(ServidorTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Tauler getTauler(){
        return t;
    }
        public static void main(String[] args) throws IOException {
        Tauler t = new Tauler();
        ServidorTCP srv = new ServidorTCP(6000, t);
        //Creamos un thread que ejecute la acción de servidor:
        Thread thServ = new Thread(()->srv.listen());
        thServ.start();
        //Creamos un servidor multicast y le pasamos el mismo taulell lo que va a hacer es mostrarlo continuamente para que se vean en tiempo real las jugadas:
        ServidorMulticast servidorMulticast = new ServidorMulticast(6001,"224.0.0.20",srv);
        servidorMulticast.runServer();
    }
}