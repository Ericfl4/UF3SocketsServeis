package a7;

import java.io.*;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class ServidorThread implements Runnable {
    private Socket clientSocket = null;
    private InputStream in = null;
    private OutputStream out = null;
    private boolean acabat;
    private Llista llista;

    public ServidorThread(Socket clientSocket)throws IOException {
        this.clientSocket = clientSocket;
        acabat = false;
        in = clientSocket.getInputStream();
        out = clientSocket.getOutputStream();
    }
    @Override
    public void run() {
        try {
            while(!acabat) {
                ObjectInputStream ois = new ObjectInputStream(in);
                try {
                    llista = (Llista) ois.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                Set<Integer> set = new HashSet<>();
                set.addAll(llista.getNumberList());
                llista.setNumberList(set.stream().toList());
                acabat=true;
            }
        }catch(IOException e){
            System.out.println(e.getLocalizedMessage());
        }
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
            objectOutputStream.writeObject(llista);
            objectOutputStream.flush();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}