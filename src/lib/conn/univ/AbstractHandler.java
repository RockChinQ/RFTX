package lib.conn.univ;

/**
 * Abstract handler included a connContext obj.
 * @author Rock Chin
 */
public abstract class AbstractHandler implements IHandler{
	private ConnContext connContext;

	public ConnContext getConnContext() {
		return connContext;
	}

	public void setConnContext(ConnContext connContext) {
		this.connContext = connContext;
	}
}
