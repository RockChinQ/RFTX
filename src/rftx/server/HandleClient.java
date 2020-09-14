package rftx.server;

import lib.conn.univ.AbstractHandler;
import lib.conn.univ.ConnContext;
import lib.conn.univ.IHandler;

/**
 * unique handler for one conn.
 * @author Rock Chin
 */
public class HandleClient extends AbstractHandler {
    String name;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * thread for handling conn
     */
    private final Thread handleConn=new Thread(()->{
        //TODO call processor
    });
    @Override
    public void handle(ConnContext object) {
        this.setConnContext(object);
        handleConn.start();
    }

    @Override
    public void dispose() {
        try {
            this.getConnContext().getSocket().close();
        }catch (Exception ignored){}
    }

    @Override
    public String getID() {
        return getName();
    }
}
