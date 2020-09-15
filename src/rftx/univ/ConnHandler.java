package rftx.univ;

import lib.conn.univ.ConnContext;
import lib.conn.univ.IHandler;

/**
 * universal handler.
 * store TransportStation here,server and client are no difference.
 * @author Rock Chin
 */
public class ConnHandler implements IHandler {
	/**
	 * way to other side of socket
	 */
	private ConnContext connContext;
	public ConnContext getConnContext() {
		return connContext;
	}

	public void setConnContext(ConnContext connContext) {
		this.connContext = connContext;
	}
	private final Thread handleConn=new Thread(()->{
		//parse command here
	});
	String name="";
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void handle(ConnContext object) {
		this.connContext=object;
		handleConn.start();
	}

	@Override
	public void dispose() {
		handleConn.stop();
	}

	@Override
	public String getID() {
		return name;
	}
}
