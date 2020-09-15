package model.conn.server;

import model.conn.univ.ConnContext;

import java.net.Socket;

/**
 * Auth methods to be called while server accepting a conn from client.
 * this should match client's auth methods
 * @author Rock Chin
 */
public interface IAuthServer {
	/**
	 * if authed successfully,return a ConnObject(created in this methods)
	 * @param socket socket from client created by server
	 * @return ConnContext of client socket
	 * @throws Exception any exception occurred while authing conn
	 */
	ConnContext auth(Socket socket)throws Exception;
}
