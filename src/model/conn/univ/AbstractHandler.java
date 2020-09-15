package model.conn.univ;

import model.transport.AbstractStation;

import java.util.ArrayList;

/**
 * Abstract handler included a connContext obj.
 * @author Rock Chin
 */
public abstract class AbstractHandler implements IHandler{

	private AbstractStation transportStation;
	public AbstractStation getTransportStation() {
		return transportStation;
	}

	public void setTransportStation(AbstractStation transportStation) {
		this.transportStation = transportStation;
	}

	private ConnContext connContext;

	public ConnContext getConnContext() {
		return connContext;
	}

	public void setConnContext(ConnContext connContext) {
		this.connContext = connContext;
	}

	private String name;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * point to server/client's connList
	 */
	private ArrayList<IHandler> handlers;

	public ArrayList<IHandler> getHandlers() {
		return handlers;
	}

	public void setHandlers(ArrayList<IHandler> handlers) {
		this.handlers = handlers;
	}
}
