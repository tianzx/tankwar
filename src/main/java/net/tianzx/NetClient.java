package net.tianzx;

import net.tianzx.test2.Test2;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

/**
 * Created by tianzx on 2016/6/10.
 */
public class NetClient {

    private static int UDP_PORT_START = 2223;
    private int udpPort;
    TankClient tc = null;
    DatagramSocket ds = null;
    public NetClient(TankClient tc){
        udpPort = UDP_PORT_START++;
        this.tc = tc;
        try {
            ds = new DatagramSocket(udpPort);
        } catch (SocketException e) {
            e.printStackTrace();
        }
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
            System.err.println("Connect to Server and server give me a ID:" +id);
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
        TankNewMsg tnm = new TankNewMsg(tc.myTank);
        send(tnm);

        new Thread(new UDPReceiveThread()).start();
    }

    public void send(TankNewMsg msg){
        msg.send("127.0.0.1",TankServer.UDP_PORT,ds);
    }

    private class UDPReceiveThread implements Runnable{
        byte[] buf = new byte[1024];
        public void run() {
            while (true){
                while (ds!=null) {
                    DatagramPacket dp =  new DatagramPacket(buf,buf.length);
                    try {
                        ds.receive(dp);
                        parse(dp);
                        System.err.println("a packet receive from server!");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        private void parse(DatagramPacket dp) {
            ByteArrayInputStream bais = new ByteArrayInputStream(buf,0,dp.getLength());
            DataInputStream dis = new DataInputStream(bais);
            TankNewMsg msg = new TankNewMsg(NetClient.this.tc);
            msg.parse(dis);
        }
    }

}
