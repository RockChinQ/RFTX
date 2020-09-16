package rftx.univ;

import model.conn.server.IClientConnectListener;
import model.conn.univ.AbstractHandler;
import model.conn.univ.ConnContext;
import model.conn.univ.IHandler;
import model.transport.AbstractStation;
import rftx.transport.ForwarderSocket;
import rftx.util.ByteArrayOperator;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * universal handler.
 * store TransportStation here,server and client are no difference.
 * @author Rock Chin
 */
public class ConnHandler extends AbstractHandler{

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
					((ForwarderSocket)getTransportStation().getFrom())
							.setBuf(ByteArrayOperator.subArray(data,1,data.length),len);
					getTransportStation().getFrom().notify();
				}else if(data[0]==(byte)1){//string data
					String msg= new String(ByteArrayOperator.subArray(data,1,data.length), StandardCharsets.UTF_8);
					getProcessor().start(msg);
				}else{
					callExceptionListener(new IllegalArgumentException("illegal message"),"illegal message");
				}
			}
		}catch (Exception e){
			if (getClientConnectListener()!=null)
				getClientConnectListener().disconnecting();
			e.printStackTrace();
			if(getClientConnectListener()!=null)
				getClientConnectListener().disconnected();
		}
	});
	public ConnHandler(ArrayList<AbstractHandler> handlers){
		this.setHandlers(handlers);
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
}
