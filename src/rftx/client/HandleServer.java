package rftx.client;


import lib.conn.univ.AbstractHandler;
import lib.conn.univ.ConnContext;

public class HandleServer extends AbstractHandler {
	@Override
	public void handle(ConnContext object) {
		this.setConnContext(object);
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
