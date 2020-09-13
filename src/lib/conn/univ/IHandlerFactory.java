package lib.conn.univ;

/**
 * Abstract factory making IHandler instance
 * IHandlerFactory's derived class should match IHandler's derived class
 * @author Rock Chin
 */
public interface IHandlerFactory {
    IHandler make(ConnObject connObject);
}
