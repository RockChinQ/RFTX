package rftx.client;

import lib.conn.client.AbstractClient;
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
public class RFTXClient extends AbstractClient implements IClient {
    /**
     * create instance by addr,port
     * @param addr server addr
     * @param port service port
     */
    public RFTXClient(String addr,int port){
        this.setAddr(addr);
        this.setPort(port);
        initDefault();
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
        initDefault();
    }

    /**
     * call to set default handle factory and auth client when creating instance
     */
    private void initDefault(){
        this.setHandlerFactory(HandlerFactory.getInstance());
        this.setAuthClient(new DefaultAuthClient());
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
