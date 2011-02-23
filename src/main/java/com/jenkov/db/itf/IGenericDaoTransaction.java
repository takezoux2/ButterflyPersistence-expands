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

/**
 *  * NOTE: Experimental!!
 * 
 * <br/><br/>
 * This interface represents a transaction that is executed inside one Mr. Persisters
 * dao instances (fx. by calling <code>IGenericDao.executeTransaction()</code>). A dao command may contain
 * multiple actions, for instance reading and updating various objects in the database.
 *
 * <br/><br/>
 * The advantage of dao commands is typically that some of the standard JDBC work can
 * be taken over by the dao instance. For instance committing or rolling back
 * the transaction and closing the connection after the transaction is executed.
 *  
 * @author Jakob Jenkov - Copyright 2005 Jenkov Development
 */
public interface IGenericDaoTransaction {

    /**
     * Executes this dao transaction.
     * @return  @return The result to be returned, if any, of this transaction.

     */
    public Object execute(IObjectDao dao) throws PersistenceException;
}
