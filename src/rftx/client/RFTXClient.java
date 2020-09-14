package rftx.client;

import lib.conn.client.IClient;
import lib.conn.univ.IHandler;
import lib.conn.univ.IHandlerFactory;

/**
 * RFTX client
 * @author Rock Chin
 */
public class RFTXClient implements IClient {
    /**
     * support ipv4
     */
    private String addr;
    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    private int port;
    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    IHandlerFactory handlerFactory;
    public IHandlerFactory getHandlerFactory() {
        return handlerFactory;
    }

    public void setHandlerFactory(IHandlerFactory handlerFactory) {
        this.handlerFactory = handlerFactory;
    }

    IHandler handler;

    public IHandler getHandler() {
        return handler;
    }

    public void setHandler(IHandler handler) {
        this.handler = handler;
    }

    /**
     * create instance by addr,port
     * @param addr server addr
     * @param port service port
     */
    public RFTXClient(String addr,int port){
        this.setAddr(addr);
        this.setPort(port);
    }
    @Override
    public void connect() {

    }
}
