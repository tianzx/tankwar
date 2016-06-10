package net.tianzx;

import net.tianzx.test2.Test2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by tianzx on 2016/6/10.
 */
public class NetClient {

    private static int UDP_PORT_START = 2223;
    private int udpPort;
    TankClient tc = null;
    public NetClient(TankClient tc){
        udpPort = UDP_PORT_START++;
        this.tc = tc;
    }
    public void connect(String ip,int port){
        Socket s =null;
        try {
             s = new Socket(ip,port);
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            dos.writeInt(udpPort);
            DataInputStream dis = new DataInputStream(s.getInputStream());
            int id = dis.readInt();
            tc.myTank.id = id;
            System.err.print("Connect to Server and server give me a ID:" +id);
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
