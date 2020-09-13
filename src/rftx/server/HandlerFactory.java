package rftx.server;

import lib.conn.univ.ConnObject;
import lib.conn.univ.IHandler;
import lib.conn.univ.IHandlerFactory;

/**
 * Client handler factory for RFTX
 * Singleton pattern
 * @author Rock Chin
 */
public class HandlerFactory implements IHandlerFactory {
    private static final HandlerFactory handlerFactory=new HandlerFactory();
    private HandlerFactory(){
    }
    public static HandlerFactory getInstance(){
        return handlerFactory;
    }
    @Override
    public IHandler make(ConnObject connObject) {
        return new HandleClient();
    }
}
