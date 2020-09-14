package rftx.client;

import lib.conn.client.IAuthClient;
import lib.conn.client.IClient;
import lib.conn.univ.ConnContext;
import lib.conn.univ.IHandler;
import lib.conn.univ.IHandlerFactory;
import lib.util.IExceptionListener;

import java.net.Socket;

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
     * exception listener
     */
    private IExceptionListener exceptionListener;
    public IExceptionListener getExceptionListener() {
        return exceptionListener;
    }

    public void setExceptionListener(IExceptionListener exceptionListener) {
        this.exceptionListener = exceptionListener;
    }
    private void callExceptionListener(Exception e,String msg){
        if (exceptionListener!=null){
            exceptionListener.exceptionCaught(e,msg);
        }
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
     * create instance by addr,port,handlerFactory,authClient
     * @param addr server addr
     * @param port service pot
     * @param handlerFactory handler factory
     * @param authClient authClient
     */
    public RFTXClient(String addr,int port,IHandlerFactory handlerFactory,IAuthClient authClient){
        this.setAddr(addr);
        this.setPort(port);
        this.setHandlerFactory(handlerFactory);
        this.setAuthClient(authClient);
    }

    /**
     * make ConnContext obj here and then auth
     * just try to connect once,if connect reset while handling conn after,throw exception there.
     * this is not a parallel method
     */
    @Override
    public void connect() {
        Socket socket;
        try {
            socket = new Socket(getAddr(), getPort());
        }catch (Exception e){
            callExceptionListener(e,"cannot connect to "+getAddr()+":"+getPort());
            return;
        }
        ConnContext connContext;
        try {
            connContext = getAuthClient().auth(socket);
        }catch (Exception authing){
            callExceptionListener(authing,"cannot send auth message.");
            return;
        }
        this.setHandler(this.getHandlerFactory().make(connContext));
        this.getHandler().handle(connContext);
    }
}
