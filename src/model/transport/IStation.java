package model.transport;

/**
 * define a Transport Station that transport data between forwarders
 * @author Rock Chin
 */
public interface IStation {
	/**
	 * run transport by FROM forwarder obj and TO forwarder obj
	 * @param from reader
	 * @param to writer
	 * @param fileTaskInfo file info
	 * @throws Exception any exception occurs while handling task
	 */
	void run(IForwarder from, IForwarder to, FileTaskInfo fileTaskInfo)throws Exception;

	/**
	 * stop transporting and dispose objects
	 * caution:close a dataIOStream points at socket,the socket will be close.
	 * @throws Exception any exception occurs while dispose task
	 */
	void dispose(IForwarder from, IForwarder to)throws Exception;
}
