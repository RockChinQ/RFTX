package conn.server;

import java.net.Socket;

public interface IAuthenticator {
	ConnObject auth(Socket socket);
}
