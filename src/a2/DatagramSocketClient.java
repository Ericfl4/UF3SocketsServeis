package a2;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class DatagramSocketClient {
    Scanner scanner = new Scanner(System.in);
    InetAddress serverIP;
    int serverPort;
    DatagramSocket socket;

    public void init(String host, int port) throws IOException {
        serverIP = InetAddress.getByName(host);
        serverPort = port;
        socket = new DatagramSocket();
    }

    public void runClient() throws IOException {
        byte [] receivedData = new byte[1024];
        byte [] sendingData;

//a l'inici
        sendingData = getFirstRequest();
//el servidor atén el port indefinidament
        while(mustContinue(sendingData)){
            DatagramPacket packet = new DatagramPacket(sendingData,
                    sendingData.length,
                    serverIP,
                    serverPort);
//enviament de la resposta
            socket.send(packet);

//creació del paquet per rebre les dades
            packet = new DatagramPacket(receivedData, 1024);
//espera de les dades
            socket.receive(packet);
//processament de les dades rebudes i obtenció de la resposta
            sendingData = getDataToRequest(packet.getData(), packet.getLength());
        }
        close();
    }

    public void close(){
        if(socket!=null && !socket.isClosed()){
            socket.close();
        }
    }
    private byte[] getDataToRequest(byte[] data, int length) {
        String s = new String(data,0,length);
        System.out.println(s);
        System.out.println("Que palabra quieres poner en mayusculas?: ");
        System.out.println("Si no quieres poner ninguna más pon adeu");
        s = scanner.nextLine();
        return s.getBytes();
    }

    private byte[] getFirstRequest() {
        System.out.println("Dime tu nombre?: ");
        String palabra = scanner.nextLine();
        return palabra.getBytes();
    }

    private boolean mustContinue(byte[] sendingData) {
        String s = new String(sendingData,0, sendingData.length);
        if (s.equals("adeu")){
            return false;
        } else {
            return true;
        }
    }

    public static void main(String[] args) throws IOException {
        DatagramSocketClient client = new DatagramSocketClient();
        client.init("192.168.56.1",4460);
        client.runClient();
    }
}