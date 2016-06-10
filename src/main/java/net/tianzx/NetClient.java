package net.tianzx;

import net.tianzx.test2.Test2;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by tianzx on 2016/6/10.
 */
public class NetClient {

    private static int UDP_PORT_START = 2223;
    private int udpPort;

    public NetClient(){
        udpPort = UDP_PORT_START++;
    }
    public void connect(String ip,int port){
        Socket s =null;
        try {
             s = new Socket(ip,port);
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            dos.writeInt(udpPort);
            System.err.print("Connect to Server");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (null!=s){
                try {
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
