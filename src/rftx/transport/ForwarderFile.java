package rftx.transport;

import model.transport.FileTaskInfo;
import model.transport.IForwarder;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * redirect file data,or to file data
 * @author Rock Chin
 */
public class ForwarderFile implements IForwarder {
	/*the first time i try,if failed,try again please!*/
	/**
	 * stored the info about
	 */
	private FileTaskInfo taskInfo;
	public FileTaskInfo getTaskInfo() {
		return taskInfo;
	}

	public void setTaskInfo(FileTaskInfo taskInfo) {
		this.taskInfo = taskInfo;
	}

	/**
	 * which file to read from,create when instance create
	 * use when this instance is a reader
	 */
	private DataInputStream inputStream;
	/**
	 * which file to write to
	 * use when this instance is a writer
	 */
	private DataOutputStream outputStream;

	/**
	 * create a reader forwarder
	 * @param info file task info
	 * @param fileInputStream file to read from
	 */
	public ForwarderFile(FileTaskInfo info,DataInputStream fileInputStream){
		this.taskInfo=info;
		this.inputStream=fileInputStream;
	}

	/**
	 * create a writer forwarder
	 * @param info file task info
	 * @param fileOutputStream file to write to
	 */
	public ForwarderFile(FileTaskInfo info,DataOutputStream fileOutputStream){
		this.taskInfo=info;
		this.outputStream=fileOutputStream;
	}

	/**
	 * return next buffer byte arr
	 * @param buf buffer byte array
	 * @return read length
	 * @throws Exception any exception
	 */
	@Override
	public int readNext(byte[] buf) throws Exception {
		return inputStream.read(buf,0,buf.length);
	}

	/**
	 *
	 * @param data buffer byte array
	 * @param length write length
	 * @throws Exception
	 */
	@Override
	public void writeNext(byte[] data,int length) throws Exception {
		outputStream.write(data,0,length);
		outputStream.flush();
	}

	@Override
	public void dispose() throws Exception {
		if(outputStream!=null){
			outputStream.close();
		}
		if (inputStream!=null){
			inputStream.close();
		}
	}
}
