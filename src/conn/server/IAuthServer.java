package conn.server;

import conn.univ.ConnObject;

import java.net.Socket;

public interface IAuthServer {
	ConnObject auth(Socket socket);
}
