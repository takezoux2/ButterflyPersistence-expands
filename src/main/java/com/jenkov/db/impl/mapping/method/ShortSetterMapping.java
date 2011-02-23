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



/**
 * @author Jakob Jenkov,  Jenkov Development
 */
package com.jenkov.db.impl.mapping.method;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A subclass of the <code>SetterMapping</code> capable of reading
 * a <code>short</code> from a <code>ResultSet</code>
 * instance and insert it into a target object, by calling the target
 * object matching setter method.
 *
 * @author Jakob Jenkov, Jenkov Development
 */

public class ShortSetterMapping extends SetterMapping{


    protected void insertValueIntoObjectDo(Object target, ResultSet result)
    throws SQLException, InvocationTargetException, IllegalAccessException {
        if(result.getObject(getColumnName()) != null){
            getObjectMethod().invoke(target, new Object[]{ new Short(result.getShort(getColumnName()))});
        }
    }

    protected Object getValueFromResultSetDo(ResultSet result) throws SQLException {
        return new Short(result.getShort(getColumnName()));
    }

}
