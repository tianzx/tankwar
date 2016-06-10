package net.tianzx;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tianzx on 2016/6/10.
 */
public class TankServer {

    public static int ID = 100;
    public static final int TCP_PORT = 8888;

    List<Client> clients = new ArrayList<Client>();

    public void start() {
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(TCP_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            Socket s = null;

            try {
                s = ss.accept();
                DataInputStream dis = null;
                dis = new DataInputStream(s.getInputStream());
                String ip = s.getInetAddress().getHostAddress();
                int udpPot = 0;
                udpPot = dis.readInt();
                Client c = new Client(ip, udpPot);
                clients.add(c);
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                dos.writeInt(ID++);
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        new TankServer().start();
    }

    private class Client {
        String ip;
        int udpPort;

        public Client(String ip, int udpPort) {
            this.ip = ip;
            this.udpPort = udpPort;
        }
    }
}
