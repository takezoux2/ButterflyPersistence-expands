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



package com.jenkov.db.impl.mapping.method;

import com.jenkov.db.itf.PersistenceException;
import com.jenkov.db.itf.mapping.ISetterMapping;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SetterMapping extends MethodMapping implements ISetterMapping{
    
       public void insertValueIntoObject(Object target, ResultSet result) throws PersistenceException{
       try {
           insertValueIntoObjectDo(target, result);
        } catch (IllegalAccessException e) {
            throw new PersistenceException("Could not set value of type <"
            + getObjectMethod().getParameterTypes()[0] + ">  of column  <"
            + getColumnName() + "> from ResultSet  on target object <" + target + ">", e);
        } catch (IllegalArgumentException e) {
           throw new PersistenceException("Could not set value of type <"
           + getObjectMethod().getParameterTypes()[0] + "> of column <"
           + getColumnName() + "> from ResultSet on target object <" + target + ">", e);
        } catch (InvocationTargetException e) {
           throw new PersistenceException("Could not set value of type <"
           + getObjectMethod().getParameterTypes()[0] + "> of column <"
           + getColumnName() + "> from ResultSet  on target object <" + target + ">", e);
        } catch (SQLException e) {
           throw new PersistenceException("Could not set value of type <"
           + getObjectMethod().getParameterTypes()[0] + "> of column <"
           + getColumnName() + "> from ResultSet on target object <" + target + ">", e);
        }
    }

    protected void insertValueIntoObjectDo(Object target, ResultSet result)
    throws SQLException, InvocationTargetException, IllegalAccessException{
          //System.out.println("MethodMapping.insertValueIntoObjectDo - default implementation does nothing.");
    }


    public Object getValueFromResultSet(ResultSet result) throws PersistenceException {
        try {
            return getValueFromResultSetDo(result);
        } catch (SQLException e) {
            throw new PersistenceException("Error getting value from ResultSet", e);
        }
    }

    protected Object getValueFromResultSetDo(ResultSet result) throws SQLException{
        return null;
    }
}
