package net.tianzx;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by tianzx on 2016/6/10.
 */
public class TankServer {

    public   static final int TCP_PORT = 8888;

    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(TCP_PORT);
            while (true) {
                Socket s = ss.accept();
                System.err.println(s.getInetAddress() +" " +s.getPort());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
