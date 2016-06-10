package net.tianzx;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

/**
 * Created by tianzx on 2016/6/10.
 */
public class TankNewMsg {

    Tank tank;

    public TankNewMsg(Tank tank){
        this.tank = tank;
    }

    public TankNewMsg(){
    }
    
    public void send(String ip,int udpPort,DatagramSocket ds) {
        ByteArrayOutputStream baos =  new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        try {
            dos.writeInt(tank.id);
            dos.writeInt(tank.x);
            dos.writeInt(tank.y);
            dos.writeInt(tank.dir.ordinal());
            dos.writeBoolean(tank.good);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] buf = baos.toByteArray();
        try {
            DatagramPacket dp = new DatagramPacket(buf,buf.length,new InetSocketAddress(ip,udpPort));
            ds.send(dp);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void parse(DataInputStream dis) {
        try {
            int id = dis.readInt();
            int x = dis.readInt();
            int y = dis.readInt();
            Direction dir = Direction.values()[dis.readInt()];
            boolean good = dis.readBoolean();
            System.err.println("id:"+id +"-"+"x:"+x+"-y:"+y+"-dir:"+dir+"-good:"+good);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
