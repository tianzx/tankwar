package net.tianzx;

import net.tianzx.test2.Test2;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by tianzx on 2016/6/10.
 */
public class NetClient {

    public void connect(String ip,int port){
        try {
            Socket s = new Socket(ip,port);
            System.err.print("Connect to Server");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
