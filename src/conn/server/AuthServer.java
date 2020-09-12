package conn.server;

import conn.univ.ConnObject;

import java.net.Socket;

/**
 * default auth that allow all conn.
 */
public class AuthServer implements IAuthServer{
    @Override
    public ConnObject auth(Socket socket)throws Exception{
        return new ConnObject(socket);
    }
}
