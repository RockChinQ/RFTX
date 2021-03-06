package rftx.univ;

import model.conn.server.IClientConnectListener;
import model.conn.univ.AbstractHandler;
import model.conn.univ.ConnContext;
import model.conn.univ.IHandler;
import model.transport.AbstractStation;
import model.transport.FileTaskInfo;
import rftx.transport.ForwarderFile;
import rftx.transport.ForwarderSocket;
import rftx.transport.TransferStation;
import rftx.util.ByteArrayOperator;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * universal handler.
 * store TransportStation here,server and client are no difference.
 * @author Rock Chin
 */
public class ConnHandler extends AbstractHandler{
	private Boolean lock=false;
	/**
	 * thread handling data from other side
	 */
	private final Thread handleConn=new Thread(()->{
		//parse command here
		try {
			byte[] data=new byte[8193];
			int len=0;
			while((len=getConnContext().getInputStream().read(data))!=-1){
				if (data[0]==(byte)0){//byte data
					System.out.println((getTransportStation()==null)+" ");
					((ForwarderSocket)getTransportStation().getFrom())
							.setBuf(ByteArrayOperator.subArray(data,1,data.length),len-1);
					synchronized (getTransportStation().getFrom()) {
						getTransportStation().getFrom().notify();
					}
				}else if(data[0]==(byte)1){//string data
					String msg= new String(ByteArrayOperator.subArray(data,1,data.length), StandardCharsets.UTF_8);
					getProcessor().start(msg.replaceAll(String.valueOf('\u0000'),""));
				}else{
					callExceptionListener(new IllegalArgumentException("illegal message"),"illegal message");
				}
				data=new byte[8193];
			}
		}catch (Exception e){
			if (getClientConnectListener()!=null)
				getClientConnectListener().disconnecting();
			e.printStackTrace();
			if(getClientConnectListener()!=null)
				getClientConnectListener().disconnected();
			try {
				getConnContext().getSocket().close();
			}catch (Exception e0){e.printStackTrace();}
		}
	});
	public ConnHandler(ArrayList<AbstractHandler> handlers){
		this.setHandlers(handlers);
		this.setTransportStation(new TransferStation());
	}
	/**
	 * processor cmd
	 */
	private CmdProcessor processor=new CmdProcessor(this);
	public CmdProcessor getProcessor() {
		return processor;
	}
	public void setProcessor(CmdProcessor processor) {
		this.processor = processor;
	}

	@Override
	public void handle(ConnContext object) {
		this.setConnContext(object);
		handleConn.start();
	}

	@Override
	public void dispose() {
		handleConn.stop();
	}

	@Override
	public String getID() {
		return getName();
	}
	public void send(String hostLink,String file,String savePath)throws Exception{
		synchronized (transportStation) {
			System.out.println("join");
			//set station
			String[] pathSpt = file.split("\\\\");
			File fromFile = new File(file);
			if (!fromFile.exists()) {
				throw new FileNotFoundException();
			}
			FileTaskInfo info = new FileTaskInfo(pathSpt[pathSpt.length - 1], fromFile.length(), savePath);
			TransferStation station = new TransferStation();
			ForwarderFile from = new ForwarderFile(info, new DataInputStream(new FileInputStream(fromFile)));
			ForwarderSocket to = new ForwarderSocket(info, getConnContext().getOutputStream());

			station.setFrom(from);
			station.setTo(to);

			setTransportStation(station);
			System.out.println("set station");
			//send msg
			String msg = "post 1 " + hostLink + " upload " + info.getName() + "|" + info.getSize() + "|" + info.getSavePath();
			getConnContext().getOutputStream().write(ByteArrayOperator.append((byte) 1, msg.getBytes(StandardCharsets.UTF_8)));
			getConnContext().getOutputStream().flush();
			synchronized (this) {
				this.wait();
			}
			System.out.println("finish send");
		}
	}

	public String reverseHostList(String[] hostList){
		StringBuffer result=new StringBuffer(hostList[hostList.length-1]);
		for(int i=hostList.length-2;i>=0;i--){
			result.append(">"+hostList[i]);
		}
		return result.toString();
	}
}
