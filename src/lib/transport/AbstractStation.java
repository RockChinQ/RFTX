package lib.transport;

/**
 * transport station's basic implementation
 * @author Rock Chin
 */
public abstract class AbstractStation implements IStation{
	private IForwarder from;
	private IForwarder to;
	public IForwarder getFrom() {
		return from;
	}
	public void setFrom(IForwarder from) {
		this.from = from;
	}
	public IForwarder getTo() {
		return to;
	}
	public void setTo(IForwarder to) {
		this.to = to;
	}
}
