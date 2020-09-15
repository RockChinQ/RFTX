package test.client;

import rftx.client.RFTXClient;

public class ClientMain {
	static RFTXClient client;
	public static void main(String[] args){
		client=new RFTXClient("dev");
		client.connect("localhost",1036);
	}
}
