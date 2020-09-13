package rftx.server;

import lib.conn.univ.ConnObject;
import lib.util.IExceptionListener;
import lib.conn.univ.IHandler;
import lib.conn.univ.IHandlerFactory;
import lib.conn.server.IAuthServer;
import lib.conn.server.IServer;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * RFTXServer,accept&save ConnObjects
 * @author Rock Chin
 */
public class RFTXServer implements IServer {
    private int port=0;
    public int getPort() {
        return port;
    }
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Auth server
     */
    private IAuthServer authServer=new AuthServer();
    public IAuthServer getAuthServer() {
        return authServer;
    }

    public void setAuthServer(IAuthServer authServer) {
        this.authServer = authServer;
    }

    /**
     * handler factory
     */
    private IHandlerFactory handlerFactory=new HandlerFactory();
    public IHandlerFactory getHandlerFactory() {
        return handlerFactory;
    }
    public void setHandlerFactory(IHandlerFactory handlerFactory) {
        this.handlerFactory = handlerFactory;
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
     * anonymous thread accepting conn
     */
    private Thread acceptConn=new Thread(()->{
        try {
            ServerSocket serverSocket=new ServerSocket(port);
            //accept
            while (true){
                Socket socket=serverSocket.accept();
                //auth conn in new thread
                try {
                    new Thread(()-> {
                        try {
                            //auth
                            ConnObject connObject = authServer.auth(socket);
                            if (connObject != null) {
                               IHandler handler=handlerFactory.make(connObject);
                               getClients().add(handler);
                               handler.handle(connObject);
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
     * ArrayList stored all client conns
     */
    private ArrayList<IHandler> clients=new ArrayList<>();
    public ArrayList<IHandler> getClients() {
        return clients;
    }

    public void setClients(ArrayList<IHandler> clients) {
        this.clients = clients;
    }

    /**
     * create instance,port provided
     * @param port server port
     */
    public RFTXServer(int port){
        this.port=port;
    }
    @Override
    public void start() {
        acceptConn.start();
    }

    @Override
    public void stop() {
        clients.forEach(IHandler::dispose);
    }
}
