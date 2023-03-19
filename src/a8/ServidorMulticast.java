package a8;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class ServidorMulticast {
    MulticastSocket socket;
    InetAddress multicastIP;
    int port;
    ServidorTCP servidorTCP;
    boolean continueRunning;

    public ServidorMulticast(int portValue, String strIp, ServidorTCP servidorTCP) throws IOException {
        socket = new MulticastSocket(portValue);
        multicastIP = InetAddress.getByName(strIp);
        port = portValue;
        this.servidorTCP = servidorTCP;
        continueRunning=true;
    }

    public void runServer() throws IOException {
        DatagramPacket packet;
        byte [] sendingData;

        while(continueRunning){
            sendingData = toByte();
            packet = new DatagramPacket(sendingData, sendingData.length, multicastIP, port);
            socket.send(packet);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.getMessage();
            }
        }
        socket.close();
    }

    public byte[] toByte() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(servidorTCP.getTauler());
        return baos.toByteArray();
    }
}