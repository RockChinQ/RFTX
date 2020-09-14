package lib.conn.client;

import lib.conn.univ.IHandler;
import lib.conn.univ.IHandlerFactory;
import lib.util.IExceptionListener;

public abstract class AbstractClient implements IClient{

	/**
	 * support ipv4
	 */
	private String addr;
	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	private int port;
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

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
	 * unique handler for unique conn to server
	 */
	private IHandler handler;

	public IHandler getHandler() {
		return handler;
	}

	public void setHandler(IHandler handler) {
		this.handler = handler;
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

}
