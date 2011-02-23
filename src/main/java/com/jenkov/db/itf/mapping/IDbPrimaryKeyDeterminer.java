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



package com.jenkov.db.itf.mapping;

import com.jenkov.db.itf.PersistenceException;

import java.sql.Connection;

/**
 * This interface represents the functions of the database primary key determiners used in Butterfly Persistence.
 * The responsibility of the database primary key determiner is to determine what column in the table is
 * the primary key. This information is stored in the coresponding object method along with the
 * table name, and is used by both the object reader and object writer for functions like
 * read-by-primary-key, insert, updateBatch, delete and delete-by-primary-key.
 *
 * <br/><br/>
 * As of now the database primary key determiner can only determine single column primary keys, and not
 * primary keys consisting of several columns.
 *
 * <br/><br/>
 * The database primary key determiner is used internally in the object mapper.
 *
 * @author Jakob Jenkov,  Jenkov Development
 */
public interface IDbPrimaryKeyDeterminer {


    /**
     * Returns a list of the columns that are part of the
     * @param table
     * @param databaseName
     * @param connection
     * @return
     * @throws PersistenceException
     */
    public IKey getPrimaryKeyMapping(String table, String databaseName, Connection connection)
    throws PersistenceException;
}
