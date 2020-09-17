package model.conn.univ;

import model.conn.server.IClientConnectListener;
import model.transport.AbstractStation;
import model.util.IExceptionListener;

import java.util.ArrayList;

/**
 * Abstract handler included a connContext obj.
 * @author Rock Chin
 */
public abstract class AbstractHandler implements IHandler{

	public AbstractStation transportStation;
	public AbstractStation getTransportStation() {
		return transportStation;
	}

	public synchronized void setTransportStation(AbstractStation transportStation){
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
	private ArrayList<AbstractHandler> handlers;

	public ArrayList<AbstractHandler> getHandlers() {
		return handlers;
	}

	public void setHandlers(ArrayList<AbstractHandler> handlers) {
		this.handlers = handlers;
	}

	/**
	 * Client connecting listener
	 * call when a client's conn is changing.
	 */
	private IClientConnectListener clientConnectListener;
	public IClientConnectListener getClientConnectListener() {
		return clientConnectListener;
	}
	public void setClientConnectListener(IClientConnectListener clientConnectListener) {
		this.clientConnectListener = clientConnectListener;
	}
	/**
	 * exception listener
	 */
	private IExceptionListener exceptionListener;
	public IExceptionListener getExceptionListener() {
		return exceptionListener;
	}

	public void setExceptionListener(IExceptionListener exceptionListener) {
		this.exceptionListener = exceptionListener;
	}
	protected void callExceptionListener(Exception e,String msg){
		if (exceptionListener!=null){
			exceptionListener.exceptionCaught(e,msg);
		}
	}

}
