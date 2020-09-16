package model.conn.server;

import model.conn.univ.AbstractHandler;
import model.conn.univ.IHandler;
import model.conn.univ.IHandlerFactory;
import model.util.IExceptionListener;

import java.util.ArrayList;

public abstract class AbstractServer implements IServer{

	private int port=0;
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * Auth server
	 */
	private IAuthServer authServer;
	public IAuthServer getAuthServer() {
		return authServer;
	}

	public void setAuthServer(IAuthServer authServer) {
		this.authServer = authServer;
	}

	/**
	 * handler factory
	 */
	private IHandlerFactory handlerFactory;
	public IHandlerFactory getHandlerFactory() {
		return handlerFactory;
	}
	public void setHandlerFactory(IHandlerFactory handlerFactory) {
		this.handlerFactory = handlerFactory;
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
	 * ArrayList stored all client conns
	 */
	private ArrayList<AbstractHandler> clients=new ArrayList<>();
	public ArrayList<AbstractHandler> getClients() {
		return clients;
	}

	public void setClients(ArrayList<AbstractHandler> clients) {
		this.clients = clients;
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
}
