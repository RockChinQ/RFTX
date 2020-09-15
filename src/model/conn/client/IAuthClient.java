package model.conn.client;

import model.conn.univ.ConnContext;

import java.net.Socket;

/**
 * Auth methods to be called by client while creating conn.
 * this should match server's auth methods
 * @author Rock Chin
 */
public interface IAuthClient {
	/**
	 * provide a socket obj created by caller,and return a ConnContext obj if auth successfully
	 * @param socket socket conn to server
	 * @return ConnContxt of server socket
	 * @throws Exception any exception occurred while authing
	 */
	ConnContext auth(Socket socket)throws Exception;
}
