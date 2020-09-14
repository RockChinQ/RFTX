package rftx.client;

import lib.conn.univ.ConnContext;
import lib.conn.univ.IHandler;
import lib.conn.univ.IHandlerFactory;

/**
 * HandlerFactory for RFTX client
 * @author Rock Chin
 */
public class HandlerFactory implements IHandlerFactory {
	private static final HandlerFactory factory=new HandlerFactory();
	private HandlerFactory(){}
	public static HandlerFactory getInstance(){
		return factory;
	}
	@Override
	public IHandler make(ConnContext connContext) {
		return new HandleServer();
	}
}
