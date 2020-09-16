package model.conn.univ;

import java.util.ArrayList;

/**
 * Abstract factory making IHandler instance
 * IHandlerFactory's derived class should match IHandler's derived class
 * @author Rock Chin
 */
public interface IHandlerFactory {
    AbstractHandler make(ConnContext connContext, ArrayList<AbstractHandler> handlersList);
}
