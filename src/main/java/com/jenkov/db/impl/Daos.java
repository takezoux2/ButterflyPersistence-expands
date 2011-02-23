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



package com.jenkov.db.impl;

import com.jenkov.db.PersistenceManager;
import com.jenkov.db.itf.*;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Jakob Jenkov - Copyright 2005 Jenkov Development
 */
public class Daos implements IDaos {

    protected PersistenceManager persistenceManager   = null;
    protected Connection                connection    = null;
    protected IPersistenceConfiguration configuration = null;
    protected IObjectDao objectDao     = null;
    protected IJdbcDao                  jdbcDao       = null;
    protected IMapDao                   mapDao        = null;


    public Daos(Connection connection, IPersistenceConfiguration configuration, PersistenceManager manager) {
        this.connection = connection;
        this.configuration = configuration;
        this.persistenceManager = manager;
        if(this.configuration.getDatabase() == null){
            this.configuration.setDatabase(Database.determineDatabase(this.connection));
        }    
    }

    public Connection getConnection() {
        return connection;
    }

    public IPersistenceConfiguration getConfiguration() {
        return configuration;
    }

    public synchronized IObjectDao getObjectDao() {
        if(this.objectDao == null){
            this.objectDao = new ObjectDao(this.connection, this.configuration);
        }
        return objectDao;
    }

    public synchronized IJdbcDao getJdbcDao() {
        if(this.jdbcDao == null){
            this.jdbcDao = new JdbcDao(this);
        }
        return jdbcDao;
    }

    public synchronized IMapDao getMapDao() {
        if(this.mapDao == null){
            this.mapDao = new MapDao(this);
        }
        return this.mapDao;
    }

    /*
    public synchronized ResultSetView getResultSetView(Object objectMappingKey, ResultSet result) throws PersistenceException {
        IObjectMapping    objectMapping = configuration.getObjectMapper().getObjectMapping(objectMappingKey, this.configuration, this.connection);
        ResultSetMetaData metaData      = null;
        Set               columns       = new LinkedHashSet();
        try {
            metaData = result.getMetaData();
            for(int i=1, n=metaData.getColumnCount(); i<=n; i++){
                String column = metaData.getColumnName(i);
                if(objectMapping.getSetterMapping(column) != null){
                    columns.add(column);
                }
            }

            ResultSetMetaDataView metaDataView = new ResultSetMetaDataView(result.getMetaData(), columns);
            return new ResultSetView(result, metaDataView);
        } catch (SQLException e) {
            throw new PersistenceException("Error creating ResultSetView for ResultSet", e);
        }
    }
    */

    public void closeConnection() throws PersistenceException {
        if(this.connection != null){
            try {
                this.connection.close();
            } catch (SQLException e) {
                throw new PersistenceException("Error closing connection", e);
            }
        }
    }
}
