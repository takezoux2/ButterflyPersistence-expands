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

import com.jenkov.db.itf.mapping.IObjectMapping;

/**
 * This interface represents all functions made available by the SQL generator in Butterfly Persistence.
 * The SQL generator generates very simple SQL so it should be compatible with most database servers.
 *
 * @author Jakob Jenkov,  Jenkov Development
 */
public interface ISqlGenerator {

    /**
     * Generates a read-by-primary-key SQL string based on the given object mapping. The SQL string
     * generated is for use with a <code>PreparedStatement</code> and thus does not contain the
     * value for the primary key itself, but a "?" character instead.
     *
     * @param mapping The object mapping to generate the read-by-primary-key SQL string for.
     * @return The <code>String</code> instance containing the generated SQL.
     * @throws PersistenceException If anything goes wrong during the SQL string generation.
     */
    public String generateReadByPrimaryKeyStatement(IObjectMapping mapping) throws PersistenceException;


    /**
     * Generates a read-by-primary-keys SQL string based on the given object mapping. The SQL string
     * generated is for use with a <code>PreparedStatement</code> and thus does not contain the
     * value for the primary key itself, but a "?" character instead.
     *
     * @param mapping The object mapping to generate the read-by-primary-key SQL string for.
     * @return The <code>String</code> instance containing the generated SQL.
     * @throws PersistenceException If anything goes wrong during the SQL string generation.
     */
    public String generateReadListByPrimaryKeysStatement(IObjectMapping mapping, int primaryKeyCount) throws PersistenceException;


    /**
     * Generates an insert SQL string based on the given object mapping. The SQL string
     * generated is for use with a <code>PreparedStatement</code> and thus does not contain the
     * values to be inserted, but a series of "?" characters instead (comma separated).
     *
     * @param mapping The object mapping to generate the insert SQL string for.
     * @return The <code>String</code> instance containing the generated SQL.
     * @throws PersistenceException If anything goes wrong during the SQL string generation.
     */
    public String generateInsertStatement(IObjectMapping mapping) throws PersistenceException;


    /**
     * Generates an updateBatch SQL string based on the given object mapping. The SQL string
     * generated is for use with a <code>PreparedStatement</code> and thus does not contain the
     * values to be updateBatch, but a series of "?" characters instead (comma separated).
     *
     * @param mapping The object mapping to generate the updateBatch SQL string for.
     * @return The <code>String</code> instance containing the generated SQL.
     * @throws PersistenceException If anything goes wrong during the SQL string generation.
     */
    public String generateUpdateStatement(IObjectMapping mapping) throws PersistenceException;


    /**
     * Generates a delete SQL string based on the given object mapping. The SQL string
     * generated is for use with a <code>PreparedStatement</code> and thus does not contain the
     * value for the primary key of the record to delete itself, but a "?" character instead.
     *
     * @param mapping The object mapping to generate the delete SQL string for.
     * @return The <code>String</code> instance containing the generated SQL.
     * @throws PersistenceException If anything goes wrong during the SQL string generation.
     */
    public String generateDeleteStatement(IObjectMapping mapping) throws PersistenceException;

}
