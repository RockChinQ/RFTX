package rftx.client;

import model.conn.client.AbstractClient;
import model.conn.client.IAuthClient;
import model.conn.client.IClient;
import model.conn.univ.ConnContext;
import model.conn.univ.IHandler;
import model.conn.univ.IHandlerFactory;
import rftx.univ.HandlerFactory;
import rftx.util.ByteArrayOperator;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * RFTX client
 * @author Rock Chin
 */
public class RFTXClient extends AbstractClient implements IClient {
    /**
     * Client name
     */
    private String name="";
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * create instance by addr,port
     */
    public RFTXClient(String name){
        this.setName(name);
        initDefault();
    }

    /**
     * create instance by name,handlersArrayList
     * this handlers array list is created already,this is design for host that need
     * to share accepted conn(as server) and created conn(as client).
     * it made server link to be possible.
     * @param name client name
     * @param handlers already created handlers array list
     */
    public RFTXClient(String name, ArrayList<IHandler> handlers){
        this.setName(name);
        this.setClients(handlers);
    }
    /**
     * create instance by addr,port,handlerFactory,authClient
     * @param handlerFactory handler factory
     * @param authClient authClient
     */
    public RFTXClient(String name,IHandlerFactory handlerFactory,IAuthClient authClient){
        this.setName(name);
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
    public void connect(String addr,int port) {
        Socket socket;
        try {
            socket = new Socket(addr, port);
        }catch (Exception e){
            callExceptionListener(e,"cannot connect to "+addr+":"+port);
            return;
        }
        ConnContext connContext;
        try {
            connContext = getAuthClient().auth(socket);
        }catch (Exception authing){
            callExceptionListener(authing,"cannot send auth message.");
            return;
        }
        IHandler handler=this.getHandlerFactory().make(connContext);
        this.getClients().add(handler);
        handler.handle(connContext);
        //send client name
        try {
            connContext.getOutputStream().write(ByteArrayOperator.append((byte) 1,("name "+getName()).getBytes(StandardCharsets.UTF_8)));
            connContext.getOutputStream().flush();
        } catch (IOException e) {
            callExceptionListener(e,"can not send name.");
        }
    }
}
