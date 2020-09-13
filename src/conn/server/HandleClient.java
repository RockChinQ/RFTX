package conn.server;

import conn.univ.ConnObject;
import conn.univ.IHandler;

/**
 * unique handler for one conn.
 * @author Rock Chin
 */
public class HandleClient implements IHandler,Runnable {
    private ConnObject connObject;
    public ConnObject getConnObject() {
        return connObject;
    }
    String name;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Override
    public void handle(ConnObject object) {
        this.connObject=object;
    }

    @Override
    public void dispose() {

    }

    @Override
    public String getID() {
        return getName();
    }

    @Override
    public void run() {

    }
}
