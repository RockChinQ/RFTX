package model.transport;

/**
 * redirect data stream.
 * e.g. file to socket,socket to socket...
 * @author Rock Chin
 */
public interface IForwarder {
	/**
	 * read next buf
	 * @param buf buffer byte array
	 * @return read length
	 * @throws Exception any Exception
	 */
	int readNext(byte[] buf)throws Exception;
	/**
	 * write next buf
	 * @param data buffer byte array
	 * @param length write length
	 * @throws Exception any Exception
	 */
	void writeNext(byte[] data,int length)throws Exception;

	/**
	 * dispose streams
	 * @throws Exception any exception while disposing
	 */
	void dispose()throws Exception;
}
