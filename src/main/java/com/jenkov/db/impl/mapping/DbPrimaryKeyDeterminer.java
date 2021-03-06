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



package com.jenkov.db.impl.mapping;

import com.jenkov.db.itf.PersistenceException;
import com.jenkov.db.itf.mapping.IDbPrimaryKeyDeterminer;
import com.jenkov.db.itf.mapping.IKey;
import com.jenkov.db.util.JdbcUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Jakob Jenkov,  Jenkov Development
 */
public class DbPrimaryKeyDeterminer implements IDbPrimaryKeyDeterminer{

    ObjectMappingFactory factory = new ObjectMappingFactory();


    public IKey getPrimaryKeyMapping(String table, String databaseName, Connection connection)
    throws PersistenceException{
        ResultSet result = null;
        IKey mapping = this.factory.createKey();
        mapping.setTable(table);


        try {
            result = connection.getMetaData().getPrimaryKeys(null, databaseName, table);

            while(result.next()){
                mapping.addColumn(result.getString(4));
            }
        } catch (SQLException e) {
            throw new PersistenceException("Error determining primary key for table " + table, e);
        } finally {
            JdbcUtil.close(result);
        }

        return mapping;
    }

}
