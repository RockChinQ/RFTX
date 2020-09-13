package rftx.server;

import lib.conn.univ.ConnContext;
import lib.conn.univ.IHandler;

/**
 * unique handler for one conn.
 * @author Rock Chin
 */
public class HandleClient implements IHandler,Runnable {
    private ConnContext connContext;
    public ConnContext getConnContext() {
        return connContext;
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
    public void handle(ConnContext object) {
        this.connContext =object;
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
