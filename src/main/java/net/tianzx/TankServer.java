package net.tianzx;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tianzx on 2016/6/10.
 */
public class TankServer {

    public   static final int TCP_PORT = 8888;

    List<Client> clients = new ArrayList<Client>();

    public void start(){
        ServerSocket ss =null;
        try {
             ss = new ServerSocket(TCP_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            Socket s = null;
            try {
                s = ss.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            DataInputStream dis = null;
            try {
                dis = new DataInputStream(s.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            String ip = s.getInetAddress().getHostAddress();
            int udpPot = 0;
            try {
                udpPot = dis.readInt();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Client c = new Client(ip,udpPot);
            clients.add(c);
            try {
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new TankServer().start();
    }

    private class Client {
        String ip;
        int udpPort;

        public  Client(String ip,int udpPort){
            this.ip = ip;
            this.udpPort=udpPort;
        }
    }
}
