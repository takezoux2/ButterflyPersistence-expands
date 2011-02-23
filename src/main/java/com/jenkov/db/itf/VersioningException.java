package com.jenkov.db.itf;

/**
 * 
 * 
 * 
 * @author takezoux2
 *
 */
public class VersioningException extends PersistenceException{

    public VersioningException(){
    }

    public VersioningException(String msg){
        super(msg);
    }

    public VersioningException(String msg, Throwable throwable){
        super(msg, throwable);
    }

    public VersioningException(Throwable throwable){
        super(throwable);
    }
}
