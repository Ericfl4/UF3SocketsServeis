/*
package a7;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClienteTCP extends Thread {
    private Llista llista;
    private Socket socket;
    private InputStream in;
    private OutputStream out;
    private boolean continueConnected;

    private ClienteTCP(String hostname, int port, Llista llista) {
        try {
            socket = new Socket(InetAddress.getByName(hostname), port);
            in = socket.getInputStream();
            out = socket.getOutputStream();
        } catch (UnknownHostException ex) {
            System.out.println("Error de connexió. No existeix el host: " + ex.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        continueConnected = true;
        this.llista=llista;
    }
    public void run() {
        while(continueConnected) {
            try {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
                objectOutputStream.writeObject(llista);
                objectOutputStream.flush();
                this.llista=getRequest();
                break;
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        close(socket);

    }
    private Llista getRequest() {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(in);
            llista = (Llista) objectInputStream.readObject();
            System.out.println(llista.leerLista());
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return llista;
    }
    private void close(Socket socket){
        try {
            if(socket!=null && !socket.isClosed()){
                if(!socket.isInputShutdown()){
                    socket.shutdownInput();
                }
                if(!socket.isOutputShutdown()){
                    socket.shutdownOutput();
                }
                socket.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(ClienteTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void main(String[] args) {
        String nombre;
        String ip;

        System.out.println("Ip del servidor, (Ejemplo:localhost):");
        Scanner scanner = new Scanner(System.in);
        ip = scanner.next();
        System.out.println("Nombre del cliente:");
        nombre = scanner.next();

        List<Integer> ints = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            ints.add(((int) (Math.random() * 20))+1);
        }
        Llista llista = new Llista(nombre,ints);

        ClienteTCP clientTcp = new ClienteTCP(ip,5000,llista);
        clientTcp.start();
    }
}

 */