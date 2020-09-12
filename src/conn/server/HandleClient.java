package conn.server;

import conn.univ.ConnObject;
import conn.univ.IHandler;

/**
 * unique handler for one conn.
 * @author Rock Chin
 */
public class HandleClient implements IHandler {
    ConnObject connObject;
    @Override
    public void handle(ConnObject object) {
        this.connObject=object;
    }

    @Override
    public void dispose() {

    }
}
