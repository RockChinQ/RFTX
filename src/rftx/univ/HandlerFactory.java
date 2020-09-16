package rftx.univ;

import model.conn.univ.AbstractHandler;
import model.conn.univ.ConnContext;
import model.conn.univ.IHandler;
import model.conn.univ.IHandlerFactory;

import java.util.ArrayList;

/**
 * Client handler factory for RFTX
 * Singleton pattern
 * @author Rock Chin
 */
public class HandlerFactory implements IHandlerFactory {
    private static final HandlerFactory handlerFactory=new HandlerFactory();
    private HandlerFactory(){}
    public static HandlerFactory getInstance(){
        return handlerFactory;
    }
    @Override
    public AbstractHandler make(ConnContext connContext, ArrayList<AbstractHandler> handlersList) {
        return new ConnHandler(handlersList);
    }
}
