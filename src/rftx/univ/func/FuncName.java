package rftx.univ.func;

import rftx.univ.CmdProcessor;
import rftx.util.cmd.AbstractFunc;
import rftx.util.cmd.AbstractProcessor;

public class FuncName implements AbstractFunc {
	@Override
	public String getFuncName() {
		return "name";
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
		((CmdProcessor)processor).getConnHandler().setName(params[0]);
		if (((CmdProcessor)processor).getConnHandler().getClientConnectListener()!=null){
			((CmdProcessor)processor).getConnHandler().getClientConnectListener().logined(params[0]);
		}
	}
}
