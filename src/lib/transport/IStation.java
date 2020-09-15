package lib.transport;

/**
 * define a Transport Station that transport data between forwarders
 * @author Rock Chin
 */
public interface IStation {
	/**
	 * start transport by FROM forwarder obj and TO forwarder obj
	 * @param from reader
	 * @param to writer
	 * @param fileTaskInfo file info
	 */
	void start(IForwarder from, IForwarder to, FileTaskInfo fileTaskInfo);

	/**
	 * stop transporting and dispose objects
	 */
	void dispose();
}
