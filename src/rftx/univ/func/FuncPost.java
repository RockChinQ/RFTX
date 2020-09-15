package rftx.univ.func;

import model.conn.univ.IHandler;
import rftx.univ.CmdProcessor;
import rftx.univ.ConnHandler;
import rftx.util.cmd.AbstractFunc;
import rftx.util.cmd.AbstractProcessor;

/**
 * handle a post request.
 * model:host0>host1>host2 name|savePath|size
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
		String[] hostList=params[0].split(">");
		//search for client
		for(IHandler lshandler:handler.getHandlers()){
			if(lshandler.getID().equals(hostList[0])){
				//target found

			}
		}
	}
}
