package lib.conn.server;

/**
 * call when events related to client occurred
 * @author Rock Chin
 */
public interface IClientConnectListener {
	/**
	 * call when server accepting a socket conn
	 */
	void connecting();

	/**
	 * call when a conn from client is authed and ready to handle
	 */
	void connected();

	/**
	 * call when server realises(depends on the specific implementation) that a client conn is disconnect
	 * server may has some routines(if any) need to handle when a client is disconnect
	 * ,this method will(should) be called before handling routines.
	 */
	void disconnecting();

	/**
	 * call when server finish all routines(if any) after client disconnecting
	 */
	void disconnected();
}
