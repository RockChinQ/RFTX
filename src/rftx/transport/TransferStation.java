package rftx.transport;

import model.transport.AbstractStation;
import model.transport.FileTaskInfo;
import model.transport.IForwarder;

public class TransferStation extends AbstractStation{
	/**
	 * this method will wait till transfer is finished.
	 * @param from reader
	 * @param to writer
	 * @param fileTaskInfo file info
	 * @throws Exception any exception
	 */
	@Override
	public void run(IForwarder from, IForwarder to, FileTaskInfo fileTaskInfo) throws Exception{
		byte[] buf=new byte[8192];
		int len=0;
		while ((len=from.readNext(buf))!=-1){
			to.writeNext(buf,len);
		}
	}
	@Override
	public void dispose(IForwarder from,IForwarder to) {
		try {
			from.dispose();
			to.dispose();
		}catch (Exception ignored){}
	}
}
