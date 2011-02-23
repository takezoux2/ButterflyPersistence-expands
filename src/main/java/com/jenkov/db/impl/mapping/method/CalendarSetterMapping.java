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

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.GregorianCalendar;

/**
 * @author Jakob Jenkov - Copyright 2004-2006 Jenkov Development
 */
public class CalendarSetterMapping extends SetterMapping{

    protected void insertValueIntoObjectDo(Object target, ResultSet result)
    throws SQLException, InvocationTargetException, IllegalAccessException {
        Timestamp timestamp = result.getTimestamp(getColumnName());
        if(timestamp != null){
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTimeInMillis(timestamp.getTime());
            getObjectMethod().invoke(target, new Object[]{calendar});
        } else {
            getObjectMethod().invoke(target, new Object[]{null});
        }
    }

    protected Object getValueFromResultSetDo(ResultSet result) throws SQLException {
        return result.getTimestamp(getColumnName());
    }


}
