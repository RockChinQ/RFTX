package rftx.server;

import lib.conn.univ.ConnContext;
import lib.conn.server.IAuthServer;

import java.net.Socket;

/**
 * default auth that allow all conn.
 * @author Rock Chin
 */
public class AuthServer implements IAuthServer {
    @Override
    public ConnContext auth(Socket socket)throws Exception{
        return new ConnContext(socket);
    }
}
