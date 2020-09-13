package conn.server;

import conn.univ.ConnObject;
import conn.univ.IHandler;
import conn.univ.IHandlerFactory;

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
