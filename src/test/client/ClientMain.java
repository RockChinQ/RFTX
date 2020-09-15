package test.client;

import rftx.client.RFTXClient;

public class ClientMain {
	static RFTXClient client;
	public static void main(String[] args){
		client=new RFTXClient("dev");
		client.connect("localhost",1036);

		byte[] t0=new byte[]{0,1,3,4};
		System.out.println(test(t0));
		System.out.println(t0[0]);
	}
	public static int test(byte[] buf){
		byte[] t1=new byte[]{20,1,2,3,4,5,6,7};
		buf=t1.clone();
		return 7;
	}
}
