package rftx.server;

import model.conn.server.AbstractServer;
import model.conn.univ.AbstractHandler;
import model.conn.univ.ConnContext;
import model.conn.univ.IHandler;
import model.conn.univ.IHandlerFactory;
import model.conn.server.IAuthServer;
import model.conn.server.IServer;
import rftx.univ.HandlerFactory;
import rftx.util.ByteArrayOperator;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * RFTXServer,accept&save ConnObjects
 * @author Rock Chin
 */
public class RFTXServer extends AbstractServer implements IServer {
    /**
     * server name
     */
    private String name="";
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * anonymous thread accepting conn
     */
    private final Thread acceptConn=new Thread(()->{
        try {
            ServerSocket serverSocket=new ServerSocket(getPort());
            //accept
            while (true){
                Socket socket=serverSocket.accept();
                if (getClientConnectListener()!=null)
                    getClientConnectListener().connecting();
                //auth conn in new thread
                try {
                    new Thread(()-> {
                        try {
                            //auth
                            ConnContext connContext = getAuthServer().auth(socket);
                            if (connContext != null) {
                                AbstractHandler handler=getHandlerFactory().make(connContext,getClients());
                                getClients().add(handler);
                                handler.handle(connContext);
                                handler.setExceptionListener(getExceptionListener());
                                handler.setClientConnectListener(getClientConnectListener());
                                //send client name
                                try {
                                    connContext.getOutputStream().write(ByteArrayOperator.append((byte) 1,("name "+getName()).getBytes(StandardCharsets.UTF_8)));
                                    connContext.getOutputStream().flush();
                                    if (getClientConnectListener()!=null)
                                        getClientConnectListener().connected();
                                } catch (IOException e) {
                                    callExceptionListener(e,"can not send name.");
                                }
                            }
                        }catch (Exception authingConn){
                            callExceptionListener(authingConn,"cannot auth conn.");
                        }
                    }).start();
                }catch (Exception makingAuthThread){
                    callExceptionListener(makingAuthThread,"cannot start to auth conn.");
                }
            }
        }catch (Exception acceptingConn){
            callExceptionListener(acceptingConn,"cannot accept conn.");
        }
    });
    /**
     * create instance,port provided
     * @param port server port
     */
    public RFTXServer(String name,int port){
        this.setName(name);
        this.setPort(port);
        initDefault();
    }
    /**
     * create instance by name,handlersArrayList
     * this handlers array list is created already,this is designed for host that need
     * to share accepted conns(as server) and created conns(as client).
     * it made "server link" to be possible.
     * @param name server name
     * @param handlers already created handlers array list
     */
    public RFTXServer(String name, ArrayList<AbstractHandler> handlers){
        this.setName(name);
        this.setClients(handlers);
    }
    /**
     * create instance by port,handlerFactory,authServer
     * @param port server port
     * @param handlerFactory handler factory
     * @param authServer authServer
     */
    public RFTXServer(String name,int port,IHandlerFactory handlerFactory,IAuthServer authServer){
        this.setName(name);
        this.setPort(port);
        this.setHandlerFactory(handlerFactory);
        this.setAuthServer(authServer);
        initDefault();
    }
    /**
     * call to set default handle factory and auth client when creating instance
     */
    private void initDefault(){
        this.setHandlerFactory(HandlerFactory.getInstance());
        this.setAuthServer(new DefaultAuthServer());
    }
    @Override
    public void start() {
        acceptConn.start();
    }

    @Override
    public void stop() {
        getClients().forEach(IHandler::dispose);
    }
}
