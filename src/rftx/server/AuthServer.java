package rftx.server;

import lib.conn.univ.ConnObject;
import lib.conn.server.IAuthServer;

import java.net.Socket;

/**
 * default auth that allow all conn.
 * @author Rock Chin
 */
public class AuthServer implements IAuthServer {
    @Override
    public ConnObject auth(Socket socket)throws Exception{
        return new ConnObject(socket);
    }
}
