package model.conn.client;

/**
 * Client interface.
 */
public interface IClient {
	/**
	 * use addr and port to create a new conn to server
	 * @param addr server addr
	 * @param port service port
	 * @throws Exception any exception
	 * @return index of new conn
	 */
	int connect(String addr,int port)throws Exception;
}
