package rftx.univ;

import rftx.univ.func.FuncName;
import rftx.univ.func.FuncPost;
import rftx.util.cmd.AbstractProcessor;
import rftx.util.cmd.Command;

/**
 * command processor for handler that handle any conn
 * means that any control message will be parsed by this class.s
 * @author Rock Chin
 */
public class CmdProcessor extends AbstractProcessor {
	private ConnHandler connHandler;
	public ConnHandler getConnHandler() {
		return connHandler;
	}

	public void setConnHandler(ConnHandler connHandler) {
		this.connHandler = connHandler;
	}
	public CmdProcessor(ConnHandler connHandler){
		this.setConnHandler(connHandler);
		register();
	}
	protected void register(){
		this.registerFunc(new FuncName());
		this.registerFunc(new FuncPost());
	}
	@Override
	protected Command parse(String cmdStr) {
		System.out.println("process:"+cmdStr);
		String[] spt=cmdStr.split(" ");
		return new Command(spt[0],subArray(spt,1,spt.length),cmdStr);
	}
	/**
	 * 取部分数组
	 * @param array 原数组
	 * @param start 起始位置(包含)
	 * @param end 终点位置(不含)
	 * @return 截取出的数组
	 */
	private String[] subArray(String[] array,int start,int end){
		String[] result=new String[end-start];
		for(int i=start;i<end;i++){
			result[i-start]=array[i];
		}
		return result;
	}
}
