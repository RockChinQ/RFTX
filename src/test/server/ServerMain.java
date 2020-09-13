package test.server;

import rftx.server.RFTXServer;

public class ServerMain {
    static RFTXServer rftxServer;
    public static void main(String[] args){
        System.out.println("Starting server...");
        rftxServer=new RFTXServer(1036);
        rftxServer.start();
    }
}
