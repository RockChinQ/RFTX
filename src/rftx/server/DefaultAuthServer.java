package rftx.server;

import model.conn.univ.ConnContext;
import model.conn.server.IAuthServer;

import java.net.Socket;

/**
 * default auth that allows any conn.
 * @author Rock Chin
 */
public class DefaultAuthServer implements IAuthServer {
    @Override
    public ConnContext auth(Socket socket)throws Exception{
        return new ConnContext(socket);
    }
}
