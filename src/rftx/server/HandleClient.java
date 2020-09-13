package rftx.server;

import lib.conn.univ.ConnObject;
import lib.conn.univ.IHandler;

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

    /**
     * thread handling conn
     */
    private final Thread handleConn=new Thread(()->{

    });
    @Override
    public void handle(ConnObject object) {
        this.connObject=object;
        handleConn.start();
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
