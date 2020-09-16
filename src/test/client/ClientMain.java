package test.client;

import model.conn.server.IClientConnectListener;
import rftx.client.RFTXClient;

public class ClientMain {
	static RFTXClient client;
	public static void main(String[] args)throws Exception{
		client=new RFTXClient("dev");
		client.setClientConnectListener(new IClientConnectListener() {
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
		client.connect("localhost",1036);

	}
	public static int test(byte[] buf){
		byte[] t1=new byte[]{20,1,2,3,4,5,6,7};
		buf=t1.clone();
		return 7;
	}
}
