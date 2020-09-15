package rftx.univ;

import lib.conn.univ.ConnContext;
import lib.conn.univ.IHandler;
import lib.transport.AbstractStation;
import rftx.util.ByteArrayOperator;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

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
			byte[] data=new byte[1024];
			int len=0;
			while((len=getConnContext().getInputStream().read(data))!=-1){
				if (data[0]==(byte)0){//byte data

				}else if(data[1]==(byte)1){//string data
					String msg= new String(ByteArrayOperator.subArray(data,1,data.length), StandardCharsets.UTF_8);
					String[] content=msg.split(" ");
				}else{
					//TODO call illegal params exception
				}
			}
		}catch (Exception e){
			//TODO add exception listener
		}
	});
	String name="";
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
