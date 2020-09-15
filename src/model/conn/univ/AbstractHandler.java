package model.conn.univ;

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

	private String name;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
