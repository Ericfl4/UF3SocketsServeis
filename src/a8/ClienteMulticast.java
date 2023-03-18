package a8;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;

public class ClienteMulticast {

    private InetAddress multicastIP;
    private int port;
    private MulticastSocket socket;
    private NetworkInterface netIf;
    private InetSocketAddress group;
    private boolean continueRunning = false;

    public ClienteMulticast(int portValue, String strIp) throws IOException {
        multicastIP = InetAddress.getByName(strIp);
        port = portValue;
        socket = new MulticastSocket(port);
        //netIf = NetworkInterface.getByName("enp1s0");
        netIf = socket.getNetworkInterface();
        group = new InetSocketAddress(strIp, portValue);
    }

    public void runClient() throws IOException {
        DatagramPacket packet;
        byte[] receivedData = new byte[1024];

        socket.joinGroup(group, netIf);
        System.out.printf("Connectat a %s:%d%n", group.getAddress(), group.getPort());

        while (!continueRunning) {
            packet = new DatagramPacket(receivedData, receivedData.length);
            socket.setSoTimeout(5000);
            try {
                socket.receive(packet);
                continueRunning = getData(packet.getData());
            } catch (SocketTimeoutException e) {
                System.out.println("S'ha perdut la connexió amb el servidor.");
                continueRunning = false;
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        socket.leaveGroup(group, netIf);
        socket.close();
    }
    protected boolean getData(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais);
        Tauler tauler = (Tauler) ois.readObject();
        System.out.println(tauler.toString());
        if(tauler.getNumPlayers() == tauler.acabats){
            System.out.println("Finalizando juego...");
            return true;
        }
        return false;
    }

}


