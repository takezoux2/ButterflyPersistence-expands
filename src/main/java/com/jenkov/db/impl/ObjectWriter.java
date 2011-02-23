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

import com.jenkov.db.itf.IObjectWriter;
import com.jenkov.db.itf.PersistenceException;
import com.jenkov.db.itf.UpdateResult;
import com.jenkov.db.itf.Database;
import com.jenkov.db.itf.VersioningException;
import com.jenkov.db.itf.mapping.IGetterMapping;
import com.jenkov.db.itf.mapping.IKeyValue;
import com.jenkov.db.itf.mapping.IObjectMapping;
import com.jenkov.db.itf.mapping.ISetterMapping;
import com.jenkov.db.itf.mapping.IVersioningMapping;
import com.jenkov.db.util.JdbcUtil;

import java.sql.*;
import java.util.Collection;
import java.util.Iterator;

/**
 *
 * todo change all connection.prepareStatementForInsert(sql) to connection.prepareStatementForInsert(sql, Statement.RETURN_GENERATED_KEYS)
 * @author Jakob Jenkov,  Jenkov Development
 */
public class ObjectWriter implements IObjectWriter{

    protected Database database = null;

    public void setDatabase(Database database) {
        this.database = database;
    }

    public UpdateResult insert(IObjectMapping mapping, Object object, String sql, Connection connection) throws PersistenceException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = prepareStatementForInsert(mapping, connection, sql, preparedStatement);
            insertObjectFieldsInStatement(mapping, object, preparedStatement);
            UpdateResult result = new UpdateResult();
            result.setAffectedRecords(new int[1]);
            result.getAffectedRecords()[0] = preparedStatement.executeUpdate();
            addGeneratedKeys(mapping, preparedStatement, result);
            return result;
        } catch (SQLException e) {
            throw new PersistenceException("Error inserting object into database. Object was: (" +
                    object.toString() + ")\nSql: " + sql, e);
        } finally {
            JdbcUtil.close(preparedStatement);
        }
    }


    public UpdateResult insertBatch(IObjectMapping mapping, Collection objects, String sql, Connection connection) throws PersistenceException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = prepareStatementForInsert(mapping, connection, sql, preparedStatement);
            Iterator iterator = objects.iterator();
            while(iterator.hasNext()){
                Object object = iterator.next();
                insertObjectFieldsInStatement(mapping, object, preparedStatement);
                preparedStatement.addBatch();
            }
            UpdateResult result = new UpdateResult();
            result.setAffectedRecords(preparedStatement.executeBatch());
            addGeneratedKeys(mapping, preparedStatement, result);
            return result;
        } catch (SQLException e) {
            throw new PersistenceException("Error batch inserting objects in database. Objects were: (" +
                    objects.toString() + ")\nSql: " + sql, e);
        } finally {
            JdbcUtil.close(preparedStatement);
        }
    }


    public UpdateResult update(IObjectMapping mapping, Object object, String sql, Connection connection) throws PersistenceException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            int parameterCount = insertObjectFieldsInStatement(mapping, object, preparedStatement);
            int versioningIndex = insertPrimaryKeyFromObject(mapping, object, preparedStatement, parameterCount);

            if(mapping.getVersiongMapping() != null){
            	insertVersioningValue(mapping ,object, preparedStatement,versioningIndex);
            }
            UpdateResult result = new UpdateResult();
            result.setAffectedRecords(new int[1]);
            result.getAffectedRecords()[0] = preparedStatement.executeUpdate();
            //addGeneratedKeys(preparedStatement, result);
            if(mapping.getVersiongMapping() != null){
            	// if no column is updated , it my be versioning error.
            	if(result.getAffectedRecords()[0] == 0){
            		throw new VersioningException("Versioning error.Not updateed.\nsql:" + sql );
            	}else{
            		mapping.getVersiongMapping().incrementVersion(mapping, object);
            	}
            }

            //addGeneratedKeys(preparedStatement, result);
            return result;
        } catch (SQLException e) {
            throw new PersistenceException("Error updating object in database. Object was: (" +
                    object.toString() + ")\nSql: " + sql, e);
        } finally {
            JdbcUtil.close(preparedStatement);
        }                                                                                 
    }

    public UpdateResult update(IObjectMapping mapping, Object object, Object oldPrimaryKeyValue,
                               String sql, Connection connection) throws PersistenceException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            int parameterCount = insertObjectFieldsInStatement(mapping, object, preparedStatement);
            int versioningIndex = insertPrimaryKeyValue(mapping, oldPrimaryKeyValue, preparedStatement, parameterCount);
            
            if(mapping.getVersiongMapping() != null){
            	insertVersioningValue(mapping ,object, preparedStatement,versioningIndex);
            }
            UpdateResult result = new UpdateResult();
            result.setAffectedRecords(new int[1]);
            result.getAffectedRecords()[0] = preparedStatement.executeUpdate();
            //addGeneratedKeys(preparedStatement, result);
            if(mapping.getVersiongMapping() != null){
            	// if no column is updated , it my be versioning error.
            	if(result.getAffectedRecords()[0] == 0){
            		throw new VersioningException("Versioning error.Not updateed.\nsql:" + sql );
            	}else{
            		mapping.getVersiongMapping().incrementVersion(mapping, object);
            	}
            }
            return result;
        } catch (SQLException e) {
            throw new PersistenceException("Error updating object in database. Object was: (" +
                    object.toString() + ")\nSql: " + sql, e);
        } finally {
            JdbcUtil.close(preparedStatement);
        }
    }


    public UpdateResult updateBatch(IObjectMapping mapping, Collection objects, String sql, Connection connection) throws PersistenceException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            Iterator iterator = objects.iterator();
            while(iterator.hasNext()){
                Object object = iterator.next();
                int parameterCount = insertObjectFieldsInStatement(mapping, object, preparedStatement);
                int versioningIndex = insertPrimaryKeyFromObject(mapping, object, preparedStatement, parameterCount);

                if(mapping.getVersiongMapping() != null){
                	insertVersioningValue(mapping ,object, preparedStatement,versioningIndex);
                }
                preparedStatement.addBatch();
            }

            UpdateResult result = new UpdateResult();
            result.setAffectedRecords(preparedStatement.executeBatch());
            //addGeneratedKeys(preparedStatement, result);
            return result;
        } catch (SQLException e) {
            throw new PersistenceException("Error batch updating objects in database. Objects were: (" +
                    objects.toString() + ")\nSql: " + sql, e);
        } finally {
            JdbcUtil.close(preparedStatement);
        }
    }


    public UpdateResult updateBatch(IObjectMapping mapping, Collection objects, Collection oldPrimaryKeys,
                             String sql, Connection connection) throws PersistenceException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            Iterator objectIterator        = objects.iterator();
            Iterator oldPrimaryKeyIterator = oldPrimaryKeys.iterator();

            while(objectIterator.hasNext()){
                Object object        = objectIterator.next();
                Object oldPrimaryKey = oldPrimaryKeyIterator.next();
                int parameterCount = insertObjectFieldsInStatement(mapping, object, preparedStatement);
                int versioningIndex = insertPrimaryKeyValue(mapping, oldPrimaryKey, preparedStatement, parameterCount);

                if(mapping.getVersiongMapping() != null){
                	insertVersioningValue(mapping ,object, preparedStatement,versioningIndex);
                }
                preparedStatement.addBatch();
            }
            UpdateResult result = new UpdateResult();
            result.setAffectedRecords(preparedStatement.executeBatch());
            //addGeneratedKeys(preparedStatement, result);
            return result;
        } catch (SQLException e) {
            throw new PersistenceException("Error batch updating objects in database. Objects were: (" +
                    objects.toString() + ")\nOld Primary Keys were: (" + oldPrimaryKeys + ")" +
                    "\nSql: " + sql, e);
        } finally {
            JdbcUtil.close(preparedStatement);
        }
    }


    public UpdateResult delete(IObjectMapping mapping, Object object, String sql, Connection connection) throws PersistenceException {
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(sql);
            int versioningIndex = insertPrimaryKeyFromObject(mapping, object, preparedStatement, 1);

            if(mapping.getVersiongMapping() != null){
            	insertVersioningValue(mapping ,object, preparedStatement,versioningIndex);
            }
            UpdateResult result = new UpdateResult();
            result.setAffectedRecords(new int[1]);
            result.getAffectedRecords()[0] = preparedStatement.executeUpdate();
            
            if(mapping.getVersiongMapping() != null){
            	// if no column is updated , it my be versioning error.
            	if(result.getAffectedRecords()[0] == 0){
            		throw new VersioningException("Versioning error.Not deleted.\nsql:" + sql );
            	}
            }
//            addGeneratedKeys(preparedStatement, result);
            return result;
         } catch(SQLException e){
             throw new PersistenceException("Error deleting object: " + object + ", using method:\n" + mapping);
         } finally {
            JdbcUtil.close(preparedStatement);
         }
     }

    public UpdateResult deleteBatch(IObjectMapping mapping, Collection objects, String sql, Connection connection) throws PersistenceException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            Iterator iterator = objects.iterator();
            while(iterator.hasNext()){
                Object object = iterator.next();
                int versioningIndex = insertPrimaryKeyFromObject(mapping, object, preparedStatement, 1);

                if(mapping.getVersiongMapping() != null){
                	insertVersioningValue(mapping ,object, preparedStatement,versioningIndex);
                }
                preparedStatement.addBatch();
            }

            UpdateResult result = new UpdateResult();
            result.setAffectedRecords(preparedStatement.executeBatch());
//            addGeneratedKeys(preparedStatement, result);
            return result;
        } catch (SQLException e) {
            throw new PersistenceException("Error batch deleting objects in database. Objects were: (" +
                    objects.toString() + ")\nSql: " + sql, e);
        } finally {
            JdbcUtil.close(preparedStatement);
        }
    }


    public UpdateResult deleteByPrimaryKey(IObjectMapping mapping, Object primaryKey, String sql, Connection connection) throws PersistenceException {
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(sql);
            int versioningIndex = insertPrimaryKeyValue(mapping, primaryKey, preparedStatement, 1);

            UpdateResult result = new UpdateResult();
            result.setAffectedRecords(new int[1]);
            result.getAffectedRecords()[0] = preparedStatement.executeUpdate();
//            addGeneratedKeys(preparedStatement, result);
            return result;
        } catch(SQLException e){
            throw new PersistenceException("Error deleting object by primary key: " + primaryKey + ", using method:\n" + mapping);
        } finally {
            JdbcUtil.close(preparedStatement);
        }
    }


    public UpdateResult deleteByPrimaryKeysBatch(IObjectMapping mapping, Collection primaryKeys, String sql, Connection connection) throws PersistenceException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            Iterator iterator = primaryKeys.iterator();
            while(iterator.hasNext()){
                Object primaryKey = iterator.next();
                insertPrimaryKeyValue(mapping, primaryKey, preparedStatement, 1);
                preparedStatement.addBatch();
            }
            UpdateResult result = new UpdateResult();
            result.setAffectedRecords(preparedStatement.executeBatch());
//            addGeneratedKeys(preparedStatement, result);
            return result;
        } catch (SQLException e) {
            throw new PersistenceException("Error batch deleting objects in database. Primary keys were: (" +
                    primaryKeys.toString() + ")\nSql: " + sql, e);
        } finally {
            JdbcUtil.close(preparedStatement);
        }
    }


     private int insertObjectFieldsInStatement(IObjectMapping mapping, Object object, PreparedStatement preparedStatement)
     throws PersistenceException {
         Iterator iterator = mapping.getGetterMappings().iterator();
         int i=1;
         while(iterator.hasNext()){
             IGetterMapping fieldMapping = (IGetterMapping) iterator.next();
             if(fieldMapping.isTableMapped() && !fieldMapping.isAutoGenerated()){
                 fieldMapping.insertValueFromObject(object, preparedStatement, i);
                 i++;
             }
         }
         return i;
     }


    private int insertPrimaryKeyFromObject(IObjectMapping mapping, Object object, PreparedStatement preparedStatement, int parameterCount) throws PersistenceException {
        Iterator iterator = mapping.getPrimaryKey().getColumns().iterator();
        while(iterator.hasNext()){
            mapping.getGetterMapping((String) iterator.next())
                    .insertValueFromObject(object, preparedStatement, parameterCount++);
        }
        return parameterCount;
    }


    private int insertPrimaryKeyValue(IObjectMapping mapping, Object oldPrimaryKeyValue, PreparedStatement preparedStatement, int parameterCount) throws PersistenceException {
        IKeyValue value = null;
        if(oldPrimaryKeyValue instanceof IKeyValue){
            value = (IKeyValue) oldPrimaryKeyValue;
        } else {
            value = mapping.getPrimaryKey().toKeyValue(oldPrimaryKeyValue);
        }
        Iterator iterator = mapping.getPrimaryKey().getColumns().iterator();
        while(iterator.hasNext()){
            String column = (String) iterator.next();
            mapping.getGetterMapping(column)
                    .insertObject(value.getColumnValue(column), preparedStatement, parameterCount++);
        }
        return parameterCount;
    }
    private int insertVersioningValue(IObjectMapping mapping , Object object, PreparedStatement preparedStatement, int parameterCount) throws PersistenceException {
    	mapping.getVersiongMapping().compareVersioning(object, preparedStatement, parameterCount);
    	return parameterCount + 1;
    }

    private void addGeneratedKeys(IObjectMapping objectMapping, PreparedStatement preparedStatement, UpdateResult result) throws SQLException {
        if(!objectMapping.hasAutoGeneratedKeys()) return;
        if(database.isPrepareStatementStatement_RETURN_GENERATED_KEYS_supported()){
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if(generatedKeys == null) return;
            while(generatedKeys.next()){
                result.getGeneratedKeys().add(generatedKeys.getObject(1));
            }
            generatedKeys.close();
        }
    }

    private PreparedStatement prepareStatementForInsert(IObjectMapping objectMapping, Connection connection, String sql, PreparedStatement preparedStatement) throws SQLException {
        if(objectMapping.hasAutoGeneratedKeys() && database.isPrepareStatementStatement_RETURN_GENERATED_KEYS_supported()){
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        } else {
            preparedStatement = connection.prepareStatement(sql);
        }
        return preparedStatement;
    }


}
