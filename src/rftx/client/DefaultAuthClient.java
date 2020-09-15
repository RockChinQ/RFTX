package rftx.client;

import model.conn.client.IAuthClient;
import model.conn.univ.ConnContext;

import java.net.Socket;

/**
 * Default auth class that send nothing.
 * matched server default auth class
 * @author Rock Chin
 */
public class DefaultAuthClient implements IAuthClient {
	@Override
	public ConnContext auth(Socket socket)throws Exception {
		return new ConnContext(socket);
	}
}
