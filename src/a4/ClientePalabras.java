package a4;

import java.io.IOException;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class ClientePalabras {
    /* Client afegit al grup multicast ServerPalabras.java que representa un velocímetre */

    private boolean continueRunning = true;
    private MulticastSocket socket;
    private InetAddress multicastIP;
    private int port;
    NetworkInterface netIf;
    InetSocketAddress group;
    Map<String, Integer> map = new HashMap<>();


    public ClientePalabras(int portValue, String strIp) throws IOException {
        multicastIP = InetAddress.getByName(strIp);
        port = portValue;
        socket = new MulticastSocket(port);
        //netIf = NetworkInterface.getByName("enp1s0");
        netIf = socket.getNetworkInterface();
        group = new InetSocketAddress(strIp,portValue);
    }

    public void runClient() throws IOException{
        DatagramPacket packet;
        byte [] receivedData = new byte[124];
        socket.joinGroup(group,netIf);

        while(continueRunning){
            packet = new DatagramPacket(receivedData, receivedData.length);
            socket.setSoTimeout(5000);
            try{
                socket.receive(packet);
                continueRunning = getData(packet.getData(),packet.getLength());
            }catch(SocketTimeoutException e){
                System.out.println("S'ha perdut la connexió amb el servidor.");
                continueRunning = false;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        socket.leaveGroup(group,netIf);
        socket.close();
    }

    protected  boolean getData(byte[] data, int length) {
        String string = new String(data,0, length);

        if (!map.containsKey(string)) {
            map.put(string, 1);
            System.out.println(string +": "+map.get(string));

        } else {
            map.put(string, map.get(string) + 1);
            System.out.println(string +": "+map.get(string));
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        ClientePalabras cvel = new ClientePalabras(5557, "224.0.11.117");
        cvel.runClient();
        System.out.println("Parat!");

    }

}