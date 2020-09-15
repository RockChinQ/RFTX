package lib.conn.client;

/**
 * Client interface.
 */
public interface IClient {
	/**
	 * use addr and port to create a new conn to server
	 * @param addr server addr
	 * @param port service port
	 */
	void connect(String addr,int port);
}
