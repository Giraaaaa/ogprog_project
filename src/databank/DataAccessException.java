/*
@author Sieben Veldeman
 */


package databank;

public class DataAccessException extends RuntimeException {
    public DataAccessException(String msg, Throwable error) {
        super(msg, error);
    }

}
