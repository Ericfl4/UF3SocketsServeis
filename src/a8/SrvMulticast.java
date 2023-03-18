package a8;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class SrvMulticast {
    MulticastSocket socket;
    InetAddress multicastIP;
    int port;
    Tauler tauler;
    boolean continueRunning = true;

    public SrvMulticast(int portValue, String strIp, Tauler tauler) throws IOException {
        socket = new MulticastSocket(portValue);
        multicastIP = InetAddress.getByName(strIp);
        port = portValue;
        this.tauler = tauler;
    }


    public void runServer() throws IOException {
        DatagramPacket packet;
        byte [] sendingData;

        while(continueRunning){
            sendingData = tobyte();
            packet = new DatagramPacket(sendingData, sendingData.length,multicastIP, port);
            socket.send(packet);
            try {
                Thread.sleep(300);
            } catch (InterruptedException ex) {
                ex.getMessage();
            }
        }
        socket.close();
    }

    public byte[] tobyte() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(tauler);
        return baos.toByteArray();
    }
}
