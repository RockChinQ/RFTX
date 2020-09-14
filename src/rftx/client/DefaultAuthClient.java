package rftx.client;

import lib.conn.client.IAuthClient;
import lib.conn.univ.ConnContext;

/**
 * Default auth class that send nothing.
 * matched server default auth class
 * @author Rock Chin
 */
public class DefaultAuthClient implements IAuthClient {
	@Override
	public void auth(ConnContext connContext) {

	}
}
