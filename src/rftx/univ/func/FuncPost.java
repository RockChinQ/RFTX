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
import java.util.Arrays;

/**
 * transport message through conn link
 * model:step host0>host1>host2 <upload|download|ok|finish|error> [params]
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
		switch (params[2].replaceAll(String.valueOf('\u0000'),"")){
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
						System.out.println();
						String msg2="post "+1+" "+reverseHostList(hostList)+" ok";
						handler.getConnContext().getOutputStream().write(ByteArrayOperator.append((byte)1,msg2.getBytes(StandardCharsets.UTF_8)));
						handler.getConnContext().getOutputStream().flush();
						//launch station
						station.run();
					} else {
						ConnHandler nextOne = index(hostList[step + 1], handler.getHandlers());
						System.out.println("index "+hostList[step+1]+" null?="+(nextOne==null));
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
					//if i am not target
					if(step<hostList.length-1) {

						System.out.println("amnot tar station");
						sendToNextHost(handler, hostList[step + 1], params);
						System.out.println("run redirect station ID:"+index(hostList[step+1],handler.getHandlers()).getID());
						((TransferStation) index(hostList[step+1],handler.getHandlers()).getTransportStation()).run();
					}else {
						System.out.println("run station");
						((TransferStation) handler.getTransportStation()).run();
						//transport done
						String msg2 = new String("post 1 " + reverseHostList(hostList) + " done");
						System.out.println(msg2);
						handler.getConnContext().getOutputStream().write(ByteArrayOperator.append((byte) 1, msg2.getBytes(StandardCharsets.UTF_8)));
						handler.getConnContext().getOutputStream().flush();
						synchronized (handler){
							handler.notify();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			}
			case "download":{

			}
			case "done":{
				try {
					//if i am not target
					System.out.println("done");
					TransferStation station=((TransferStation)handler.getTransportStation());
					if (step < hostList.length - 1){
						//this operation will make station thread stop
						synchronized (station.getFrom()) {
							System.out.println("done exit socket2socket station");
							((ForwarderSocket) station.getFrom()).setBuf(new byte[]{0}, -1);
							station.getFrom().notify();
						}
						sendToNextHost(handler, hostList[step + 1], params);
					}else{
						//你idea说我在局部变量上同步，我要是不这样做我能编译通过？
						synchronized (station.getFrom()) {
							System.out.println("done dispose socket2file station");
							((ForwarderSocket) station.getFrom()).setBuf(new byte[]{0}, -1);
							station.getFrom().notify();
							station.getTo().dispose();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
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
				+params[1]);
		//pack control msg
		for(int i=2;i<params.length;i++){
			msg.append(" "+params[i]);
		}
		nextHost.getConnContext().getOutputStream().write(ByteArrayOperator.append((byte) 1,msg.toString().getBytes(StandardCharsets.UTF_8)));
		nextHost.getConnContext().getOutputStream().flush();
	}
	public ConnHandler index(String name,ArrayList<AbstractHandler> handlers){
		for(AbstractHandler handler:handlers){
			System.out.println("      indexing:"+handler.getID()+" name:"+name+" equals:"+handler.getID().equals(name));
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
	public String getHostListStr(String[] hostList){
		StringBuffer result=new StringBuffer(hostList[0]);
		for(int i=1;i<hostList.length;i++){
			result.append(">"+hostList[i]);
		}
		return result.toString();
	}
}
