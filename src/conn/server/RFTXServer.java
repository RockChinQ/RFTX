package conn.server;

/**
 * RFTXServer,accept&save ConnObjects
 * @author Rock Chin
 */
public class RFTXServer implements IServer{
    private int port=0;
    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
    private Thread acceptConn=new Thread(()->{

    });
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

    }
}
