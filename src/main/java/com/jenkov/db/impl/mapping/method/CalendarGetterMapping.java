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

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;

/**
 * @author Jakob Jenkov - Copyright 2004-2006 Jenkov Development
 */
public class CalendarGetterMapping extends GetterMapping{

    protected void insertObjectDo(Object value, PreparedStatement statement, int index) throws SQLException {
        if(value != null){
            statement.setTimestamp(index, new java.sql.Timestamp( ((Calendar) value).getTimeInMillis()));
        } else {
            statement.setTimestamp(index, null);
        }
    }

}
