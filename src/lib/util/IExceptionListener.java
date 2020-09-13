package lib.util;

/**
 * called by server when exception occurred while running.
 * @author Rock Chin
 */
public interface IExceptionListener {
    void exceptionCaught(Exception e,String message);
}
