package rftx.client;

import lib.conn.univ.ConnContext;
import lib.conn.univ.IHandler;
import lib.conn.univ.IHandlerFactory;

public class HandlerFactory implements IHandlerFactory {
	@Override
	public IHandler make(ConnContext connContext) {
		return new HandleServer();
	}
}
