package rftx.univ.func;

import model.conn.univ.AbstractHandler;
import model.conn.univ.IHandler;
import rftx.univ.CmdProcessor;
import rftx.univ.ConnHandler;
import rftx.util.cmd.AbstractFunc;
import rftx.util.cmd.AbstractProcessor;

/**
 * handle a post request.
 * model:step host0>host1>host2 name|savePath|size
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
		/**
		 * 检查是不是自己，且长度等于1
		 *  最后一个是自己则执行open指令
		 * 第一个是自己，且长度大于1
		 *  搜索第二个，是否找到
		 *      找到，step++，转发到第二个host
		 *  找不到
		 *      抛出异常
		 */
		ConnHandler handler=((CmdProcessor)processor).getConnHandler();
		int step=Integer.parseInt(params[0]);
		String[] hostList=params[1].split(">");
		if (step==hostList.length-1){//this host is target.

		}else {
			String lookfor=hostList[step+1];
			for(AbstractHandler handler1:handler.getHandlers()){
				if (handler1.getID().equals(lookfor)){
					StringBuffer nextMsg=new StringBuffer(step+1+" "+params[1]);
				}
			}
		}
	}
}
