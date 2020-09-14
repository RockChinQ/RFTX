package test.client;

import rftx.client.RFTXClient;

public class ClientMain {
	static RFTXClient client;
	public static void main(String[] args){
		client=new RFTXClient("localhost",1036);
		client.connect();
	}
}
