package rftx.univ.func;

import model.conn.univ.AbstractHandler;
import model.transport.FileTaskInfo;
import rftx.transport.ForwarderFile;
import rftx.transport.ForwarderSocket;
import rftx.transport.TransferStation;
import rftx.univ.CmdProcessor;
import rftx.univ.ConnHandler;
import rftx.util.ByteArrayOperator;
import rftx.util.cmd.AbstractFunc;
import rftx.util.cmd.AbstractProcessor;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * transport message through conn link
 * model:step host0>host1>host2 <upload|download|ok|error> [params]
 * @author Rock Chin
 */
public class FuncPost implements AbstractFunc {
	@Override
	public String getFuncName() {
		return "post";
	}

	@Override
	public String[] getParamsModel() {
		return new String[0];
	}

	@Override
	public String getDescription() {
		return null;
	}

	@Override
	public int getMinParamsAmount() {
		return 1;
	}

	@Override
	public void run(String[] params, String cmd, AbstractProcessor processor) {
		ConnHandler handler=((CmdProcessor)processor).getConnHandler();
		int step=Integer.parseInt(params[0]);
		String[] hostList=params[1].split(">");
		switch (params[2]){
			case "upload":{
				/*如果是目标，则向源发送ok消息，并设置socket到file中转站。
				* 不是目标，则搜索目标:找到，向目标发送post，设置socket到socket中转站。
				*                   未找到，向源发error
				* */
				//create file task info obj
				try {
					String infoSpt[] = arrayToString(params, 3, params.length).split("\\|");
					FileTaskInfo info = new FileTaskInfo(infoSpt[0], Long.parseLong(infoSpt[1]), infoSpt[2]);
					//am i the target?
					if (step == hostList.length - 1) {
						System.out.println("open socket2file station");
						//open socket2file station
						TransferStation station = new TransferStation();
						station.setFrom(new ForwarderSocket(info));
						//make sure dir is exist
						if(!new File(info.getSavePath().replaceAll(String.valueOf('\u0000'),"")).exists()) {
							System.out.println("mkdirs "+info.getSavePath());
							new File(info.getSavePath().replaceAll(String.valueOf('\u0000'),"")).mkdirs();
						}
						File target=new File((info.getSavePath()+File.separatorChar+info.getName()).replaceAll(String.valueOf('\u0000'),""));
						station.setTo(new ForwarderFile(info, new DataOutputStream(new FileOutputStream(target))));
						handler.setTransportStation(station);
						//send ok here
						String msg="post "+1+" "+reverseHostList(hostList)+" ok";
						handler.getConnContext().getOutputStream().write(ByteArrayOperator.append((byte)1,msg.getBytes(StandardCharsets.UTF_8)));
						handler.getConnContext().getOutputStream().flush();
						//launch station
						station.run();
					} else {
						ConnHandler nextOne = index(hostList[step + 1], handler.getHandlers());
						TransferStation station = new TransferStation();
						station.setFrom(new ForwarderSocket(info));
						station.setTo(new ForwarderSocket(info,nextOne.getConnContext().getOutputStream()));
						handler.setTransportStation(station);
						sendToNextHost(handler, hostList[step + 1], params);
					}
				}catch (Exception e){
					String msg="post "+1+" "+reverseHostList(hostList)+" error cannotOpenStation@"+step+":"+handler.getID();
					try {
						handler.getConnContext().getOutputStream().write(ByteArrayOperator.append((byte)1,msg.getBytes(StandardCharsets.UTF_8)));
						handler.getConnContext().getOutputStream().flush();
					} catch (IOException ioException) {
					}
					e.printStackTrace();
				}
				break;
			}
			case "ok":{
				//send same msg to next host,then run transport station
				try {
					sendToNextHost(handler,hostList[step+1],params);
					((TransferStation)handler.getTransportStation()).run();
				} catch (Exception e) {
				}
				break;
			}
		}
	}

	/**
	 *
	 * @param from where this msg from
	 * @param params msg params
	 */
	public void sendToNextHost(ConnHandler from,String nextName,String[] params)throws Exception{
		AbstractHandler nextHost=index(nextName,from.getHandlers());
		StringBuffer msg=new StringBuffer("post "+(Integer.parseInt(params[0])+1)+" "
				+reverseHostList(params[1].split(">")));
		//pack control msg
		for(int i=2;i<params.length;i++){
			msg.append(" "+params[i]);
		}
		nextHost.getConnContext().getOutputStream().write(ByteArrayOperator.append((byte) 1,msg.toString().getBytes(StandardCharsets.UTF_8)));
		nextHost.getConnContext().getOutputStream().flush();
	}
	public ConnHandler index(String name,ArrayList<AbstractHandler> handlers){
		for(AbstractHandler handler:handlers){
			if(handler.getID().equals(name)){
				return (ConnHandler)handler;
			}
		}
		return null;
	}
	public String reverseHostList(String[] hostList){
		StringBuffer result=new StringBuffer(hostList[hostList.length-1]);
		for(int i=hostList.length-2;i>=0;i--){
			result.append(">"+hostList[i]);
		}
		return result.toString();
	}
	public String arrayToString(String[] arr,int start,int end){
		StringBuffer result=new StringBuffer();
		for(int i=start;i<end;i++){
			result.append(arr[i]+(i==end-1?"":" "));
		}
		return result.toString();
	}
}
