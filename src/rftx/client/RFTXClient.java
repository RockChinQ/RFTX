package rftx.client;

import lib.conn.client.IAuthClient;
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

    /**
     * Handler factory
     */
    IHandlerFactory handlerFactory=HandlerFactory.getInstance();
    public IHandlerFactory getHandlerFactory() {
        return handlerFactory;
    }

    public void setHandlerFactory(IHandlerFactory handlerFactory) {
        this.handlerFactory = handlerFactory;
    }

    /**
     * unique handler for unique conn to server
     */
    IHandler handler;

    public IHandler getHandler() {
        return handler;
    }

    public void setHandler(IHandler handler) {
        this.handler = handler;
    }

    /**
     * send auth message while making conn
     */
    IAuthClient authClient=new DefaultAuthClient();
    public IAuthClient getAuthClient() {
        return authClient;
    }

    public void setAuthClient(IAuthClient authClient) {
        this.authClient = authClient;
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

    /**
     * make ConnContext obj here and then auth
     */
    @Override
    public void connect() {

    }
}
