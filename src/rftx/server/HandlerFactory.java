package rftx.server;

import lib.conn.univ.ConnObject;
import lib.conn.univ.IHandler;
import lib.conn.univ.IHandlerFactory;

/**
 * Client handler factory for RFTX
 * @author Rock Chin
 */
public class HandlerFactory implements IHandlerFactory {
    @Override
    public IHandler make(ConnObject connObject) {
        return new HandleClient();
    }
}
