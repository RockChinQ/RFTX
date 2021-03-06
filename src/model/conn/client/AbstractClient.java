package model.conn.client;

import model.conn.server.IClientConnectListener;
import model.conn.univ.AbstractHandler;
import model.conn.univ.IHandler;
import model.conn.univ.IHandlerFactory;
import model.util.IExceptionListener;

import java.util.ArrayList;

public abstract class AbstractClient implements IClient{

	/**
	 * Handler factory
	 */
	private IHandlerFactory handlerFactory;
	public IHandlerFactory getHandlerFactory() {
		return handlerFactory;
	}

	public void setHandlerFactory(IHandlerFactory handlerFactory) {
		this.handlerFactory = handlerFactory;
	}
	/**
	 * send auth message while making conn
	 */
	private IAuthClient authClient;
	public IAuthClient getAuthClient() {
		return authClient;
	}

	public void setAuthClient(IAuthClient authClient) {
		this.authClient = authClient;
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
	 * ArrayList stored all client conns
	 */
	private ArrayList<AbstractHandler> clients=new ArrayList<>();
	public ArrayList<AbstractHandler> getClients() {
		return clients;
	}

	public void setClients(ArrayList<AbstractHandler> clients) {
		this.clients = clients;
	}

}
