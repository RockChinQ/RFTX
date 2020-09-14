package rftx.client;


import lib.conn.univ.AbstractHandler;
import lib.conn.univ.ConnContext;

/**
 * Handle conn between client and server
 * @author Rock Chin
 */
public class HandleServer extends AbstractHandler {
	/**
	 * thread for handling conn
	 */
	private final Thread handleConn=new Thread(()->{
		//TODO class processor
	});
	@Override
	public void handle(ConnContext object) {
		this.setConnContext(object);
		handleConn.start();
	}
	@Override
	public void dispose() {
		try {
			getConnContext().getSocket().close();
		}catch (Exception ignored){}
	}
	@Override
	public String getID() {
		return null;
	}
}
