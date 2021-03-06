package model.conn.univ;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Package conn,dataIOStream
 * @author Rock Chin
 */
public class ConnContext {
	private DataInputStream inputStream;
	private DataOutputStream outputStream;
	private Socket socket;
	/**
	 * create instance by socket
	 * @param socket conn socket
	 * @throws IOException
	 */
	public ConnContext(Socket socket)throws IOException {
		this.socket=socket;
		this.inputStream=new DataInputStream(socket.getInputStream());
		this.outputStream=new DataOutputStream(socket.getOutputStream());
	}

	/**
	 * create instance by socket,dataInputStream and dataOutputStream
	 * @param socket conn socket
	 * @param inputStream already created inputStream
	 * @param outputStream already created outputStream
	 */
	public ConnContext(Socket socket, DataInputStream inputStream, DataOutputStream outputStream){
		this.socket=socket;
		this.inputStream=inputStream;
		this.outputStream=outputStream;
	}

	public DataInputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(DataInputStream inputStream) {
		this.inputStream = inputStream;
	}

	public DataOutputStream getOutputStream() {
		return outputStream;
	}

	public void setOutputStream(DataOutputStream outputStream) {
		this.outputStream = outputStream;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}
}
