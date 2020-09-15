package rftx.server;

import lib.conn.server.AbstractServer;
import lib.conn.univ.ConnContext;
import lib.conn.univ.IHandler;
import lib.conn.univ.IHandlerFactory;
import lib.conn.server.IAuthServer;
import lib.conn.server.IServer;
import rftx.univ.HandlerFactory;
import rftx.util.ByteArrayOperator;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

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
                //auth conn in new thread
                try {
                    new Thread(()-> {
                        try {
                            //auth
                            ConnContext connContext = getAuthServer().auth(socket);
                            if (connContext != null) {
                               IHandler handler=getHandlerFactory().make(connContext);
                               getClients().add(handler);
                               handler.handle(connContext);
                                //send client name
                                try {
                                    connContext.getOutputStream().write(ByteArrayOperator.append((byte) 1,("name "+getName()).getBytes(StandardCharsets.UTF_8)));
                                    connContext.getOutputStream().flush();
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
