package a4;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class ServerPalabras {
    MulticastSocket socket;
    InetAddress multicastIP;
    int port;
    boolean continueRunning = true;
    static String[] cars;

    public ServerPalabras(int portValue, String strIp) throws IOException {
        socket = new MulticastSocket(portValue);
        multicastIP = InetAddress.getByName(strIp);
        port = portValue;
    }

    public int randomNum(){
        return (int) (Math.random()*cars.length);
    }
    public void runServer() throws IOException{
        DatagramPacket packet;
        byte [] sendingData;

        while(continueRunning){
            sendingData = cars[randomNum()].getBytes();
            packet = new DatagramPacket(sendingData, sendingData.length,multicastIP, port);
            socket.send(packet);
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                ex.getMessage();
            }
        }
        socket.close();
    }

    public static void main(String[] args) throws IOException {
        ServerPalabras srvVel = new ServerPalabras(5557, "224.0.11.117");
        cars = new String[]{"Volvo", "BMW", "Ford", "Mazda", "Ferrari", "Lamborghini"};
        System.out.println("Servidor actiu!");
        srvVel.runServer();
        System.out.println("Parat!");
    }
}