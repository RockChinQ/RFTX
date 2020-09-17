package test.client;

import model.conn.server.IClientConnectListener;
import rftx.client.RFTXClient;
import rftx.univ.ConnHandler;

public class Client2 {
	static RFTXClient client;
	public static void main(String[] args)throws Exception{

		client=new RFTXClient("dev2");
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
		int first=client.connect("localhost",1036);
		/*client.getClients().get(0).getConnContext().getOutputStream()
				.write(ByteArrayOperator.append((byte)1,"post 1 dev>dev-server upload test.txt|11111|testPath".getBytes(StandardCharsets.UTF_8)));
		client.getClients().get(0).getConnContext().getOutputStream().flush();*/
	}
}
