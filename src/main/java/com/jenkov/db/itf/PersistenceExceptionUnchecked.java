/*
    Copyright 2008 Jenkov Development

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/



package com.jenkov.db.itf;

import java.sql.SQLException;

/**
 * An unchecked version of PersistenceException. Used by the unchecked dao command executor.
 * Usually wraps a PersistenceException.
 *
 * <br/><br/>
 * Call getPersistenceException() to obtain it if there is one. The wrapped PersistenceException
 * may have connection rollback and close exceptions attached.
 * You can also use the shortcut methods getConnectionRollbackException() and getConnectionCloseException().
 *
 *
 * <br/><br/>
 * See PersistenceException for more information.
 *
 *
 * @author Jakob Jenkov, Jenkov Development
 */
public class PersistenceExceptionUnchecked extends RuntimeException{

    public PersistenceExceptionUnchecked(){
    }

    public PersistenceExceptionUnchecked(String msg){
        super(msg);
    }

    public PersistenceExceptionUnchecked(String msg, Throwable throwable){
        super(msg, throwable);
    }

    public PersistenceExceptionUnchecked(Throwable throwable){
        super(throwable);
    }

    /**
     * Returns the wrapped PersistenceException if any.
     * @return The wrapped PersistenceException if any.
     */
    public PersistenceException getPersistenceException(){
        if(getCause() instanceof PersistenceException){
            return (PersistenceException) getCause();
        }
        return null;
    }

    /**
     * If the wrapped exception is a PersistenceException, then this
     * method returns getPersistenceException().getConnectionRollbackException();
     * Else it returns null;
     * @return The connection rollback connection of the wrapped PersistenceException or null.
     */
    public SQLException getConnectionRollbackException(){
        if(getPersistenceException() != null) {
            return getPersistenceException().getConnectionRollbackException();
        }
        return null;
    }

    /**
     * If the wrapped exception is a PersistenceException, then this
     * method returns getPersistenceException().getConnectionCloseException();
     * Else it returns null;
     * @return The connection close connection of the wrapped PersistenceException or null.
     */
    public SQLException getConnectionCloseException(){
        if(getPersistenceException() != null) {
            return getPersistenceException().getConnectionCloseException();
        }
        return null;
    }

}
