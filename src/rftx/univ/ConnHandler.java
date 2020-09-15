package rftx.univ;

import model.conn.univ.ConnContext;
import model.conn.univ.IHandler;
import model.transport.AbstractStation;
import rftx.util.ByteArrayOperator;

import java.nio.charset.StandardCharsets;

/**
 * universal handler.
 * store TransportStation here,server and client are no difference.
 * @author Rock Chin
 */
public class ConnHandler implements IHandler {
	private AbstractStation transportStation;
	public AbstractStation getTransportStation() {
		return transportStation;
	}

	public void setTransportStation(AbstractStation transportStation) {
		this.transportStation = transportStation;
	}
	/**
	 * way to other side of socket
	 */
	private ConnContext connContext;
	public ConnContext getConnContext() {
		return connContext;
	}

	public void setConnContext(ConnContext connContext) {
		this.connContext = connContext;
	}

	/**
	 * thread handling data from other side
	 */
	private final Thread handleConn=new Thread(()->{
		//parse command here
		try {
			byte[] data=new byte[1025];
			int len=0;
			while((len=getConnContext().getInputStream().read(data))!=-1){
				if (data[0]==(byte)0){//byte data

				}else if(data[1]==(byte)1){//string data
					String msg= new String(ByteArrayOperator.subArray(data,1,data.length), StandardCharsets.UTF_8);
					getProcessor().start(msg);
				}else{
					//TODO call illegal params exception
					throw new IllegalArgumentException("illegal message");
				}
			}
		}catch (Exception e){
			//TODO add exception listener
			e.printStackTrace();
		}
	});
	String name="";
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
		this.connContext=object;
		handleConn.start();
	}

	@Override
	public void dispose() {
		handleConn.stop();
	}

	@Override
	public String getID() {
		return name;
	}
}
