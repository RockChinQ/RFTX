package lib.conn.univ;

/**
 * Universal socket handler.
 * use to handle messages from another side of socket.
 * @author Rock Chin
 */
public interface IHandler {
	/**
	 * start handle routine after making socket conn.
	 * @param object connObject created by auth methods
	 */
	void handle(ConnContext object);

	/**
	 * stop handle routine and dispose memory
	 */
	void dispose();
	String getID();
}
