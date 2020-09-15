package lib.transport;

/**
 * redirect data stream.
 * e.g. file to socket,socket to socket...
 * @author Rock Chin
 */
public interface IForwarder {
	byte[] read()throws Exception;
	void write(byte[] data)throws Exception;
}
