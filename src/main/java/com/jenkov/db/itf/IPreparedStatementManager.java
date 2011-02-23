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

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Represents a manager capable of preparing, initializing parameters of,
 * executing and post processing a PreparedStatement.
 *
 * <br/><br/>
 * In most cases you can just extend the PreparedStatementManagerBase which
 * has default implementations for all four methods. Then you just
 * override the methods you need. Typically it will be the init method.
 *
 * @author Jakob Jenkov - Copyright 2005 Jenkov Development
 */
public interface IPreparedStatementManager {

    /**
     * Returns a PreparedStatement prepared from the SQL and the connection.
     * Implement this method if the default implementation in PreparedStatementManagerBase
     * doesn't match your needs. For instance, if you need to call a different
     * Connection.prepare(...) method.
     *
     * @param sql             The SQL to prepare the PreparedStatement for.
     * @param connection      The connection to use to prepare the PreparedStatement.
     * @return                A PreparedStatement.
     * @throws SQLException   If the preparation fails.
     * @throws PersistenceException If something else goes wrong during the prepare method.
     */
    public PreparedStatement prepare    (String            sql, Connection connection) throws SQLException, PersistenceException;

    /**
     * Initializes the PreparedStatement. Typicially this means setting the correct parameters
     * on the statement (setString(1, "value"), setLong(1, 45) etc.).
     *
     * @param statement     The PreparedStatement to initialize.
     * @throws SQLException If the initialization fails in the JDBC driver.
     * @throws PersistenceException If something else goes wrong during initialization.
     */
    public void              init       (PreparedStatement statement                 ) throws SQLException, PersistenceException;

    /**
     * Executes the PreparedStatement.
     * @param statement The PreparedStatement to execute.
     * @return          The result of the execute() method. For instance a ResultSet.
     * @throws SQLException If the executing fails.
     * @throws PersistenceException If something else fails during execution.
     */
    public Object            execute    (PreparedStatement statement                 ) throws SQLException, PersistenceException;

    /**
     * Post processes the PreparedStatement after execution. Typically post processing
     * will be reading of generated id's. You do not need to close the PreparedStatement
     * in this method.
     *
     * @param statement      The PreparedStatement to post process.
     * @throws SQLException  If post processing fails in the driver.
     * @throws PersistenceException If something else fails during post processing.
     */
    public void              postProcess(PreparedStatement statement                 ) throws SQLException, PersistenceException;

}
