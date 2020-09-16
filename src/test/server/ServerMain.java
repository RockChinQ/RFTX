package test.server;

import model.conn.server.IClientConnectListener;
import rftx.server.RFTXServer;

public class ServerMain {
    static RFTXServer rftxServer;
    public static void main(String[] args){
        System.out.println("Starting server...");
        rftxServer=new RFTXServer("dev-server",1036);
        rftxServer.setClientConnectListener(new IClientConnectListener() {
            @Override
            public void connecting() {
                System.out.println("新连接...");
            }

            @Override
            public void connected() {
                System.out.println("已接受");
            }

            @Override
            public void disconnecting() {

                System.out.println("断开中...");
            }

            @Override
            public void disconnected() {
                System.out.println("已断开");

            }

            @Override
            public void logined(String id) {
                System.out.println("收到id:"+id);
            }
        });
        rftxServer.start();
    }
}
