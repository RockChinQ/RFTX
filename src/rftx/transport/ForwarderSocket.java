package rftx.transport;

import model.transport.FileTaskInfo;
import model.transport.IForwarder;
import rftx.util.ByteArrayOperator;

import java.io.DataOutputStream;

/**
 * redirect data from socket or to
 * @author Rock Chin
 */
public class ForwarderSocket implements IForwarder {
	private static final long BUFFER_LENGTH=8192;
	/**
	 * buf,will be set by handler
	 * because data read from socket included flags for handler to recognize.
	 */
	byte[] buf=null;
	int len=0;
	public byte[] getBuf() {
		return buf;
	}
	public int getLen(){
		return len;
	}

	public void setBuf(byte[] buf,int len) {
		if(buf.length!=BUFFER_LENGTH){
			throw new IllegalArgumentException("buf length should be "+BUFFER_LENGTH+".");
		}
		this.buf = buf;
		this.len=len;
	}

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
	 * create a read forwarder
	 * @param info file task info
	 */
	public ForwarderSocket(FileTaskInfo info){
		this.setTaskInfo(info);
	}

	/**
	 * create a write forwarder
	 * @param info
	 * @param outputStream
	 */
	public ForwarderSocket(FileTaskInfo info,DataOutputStream outputStream){
		this.setTaskInfo(info);
		this.outputStream=outputStream;
	}
	/**
	 *
	 * @param buf buffer byte array
	 * @return
	 * @throws Exception
	 */
	@Override
	public int readNext(byte[] buf) throws Exception {
		if(buf.length!=BUFFER_LENGTH){
			throw new IllegalArgumentException("buf length should be "+BUFFER_LENGTH+".");
		}
		while (true){
			synchronized (this) {
				if (this.buf == null) {
					wait(500);
				} else {
					for (int i = 0; i < BUFFER_LENGTH; i++) {
						buf[i] = this.buf[i];
					}
					this.buf = null;
					return len;
				}
			}
		}
	}
	/**
	 * which file to write to
	 * use when this instance is a writer
	 */
	private DataOutputStream outputStream;
	/**
	 *
	 * @param data buffer byte array
	 * @param length write length
	 * @throws Exception
	 */
	@Override
	public void writeNext(byte[] data, int length) throws Exception {
		outputStream.write(ByteArrayOperator.append((byte)0,data),0,length+1);
		outputStream.flush();
	}

	@Override
	public void dispose() throws Exception {
		if(outputStream!=null){
			outputStream.close();
		}
	}
}
