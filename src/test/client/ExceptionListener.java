package test.client;

import lib.util.IExceptionListener;

public class ExceptionListener implements IExceptionListener {
	@Override
	public void exceptionCaught(Exception e, String message) {
		System.out.println(e.getMessage()+" "+message);
	}
}
