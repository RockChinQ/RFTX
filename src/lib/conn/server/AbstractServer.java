package lib.conn.server;

import lib.conn.univ.IHandler;
import lib.conn.univ.IHandlerFactory;
import lib.util.IExceptionListener;

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
	private ArrayList<IHandler> clients=new ArrayList<>();
	public ArrayList<IHandler> getClients() {
		return clients;
	}

	public void setClients(ArrayList<IHandler> clients) {
		this.clients = clients;
	}

}
