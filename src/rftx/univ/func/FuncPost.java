package rftx.univ.func;

import rftx.util.cmd.AbstractFunc;
import rftx.util.cmd.AbstractProcessor;

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

	}
}
