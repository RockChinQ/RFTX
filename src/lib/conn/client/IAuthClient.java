package lib.conn.client;

import lib.conn.univ.ConnContext;

/**
 * Auth methods to be called by client while creating conn.
 * this should match server's auth methods
 * @author Rock Chin
 */
public interface IAuthClient {
	void auth(ConnContext connContext);
}
