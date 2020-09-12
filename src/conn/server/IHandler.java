package conn.server;

public interface IHandler {
	void handle(ConnObject object);
	void dispose();
}
