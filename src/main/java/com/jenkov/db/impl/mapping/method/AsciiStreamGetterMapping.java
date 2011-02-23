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

import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 * A subclass of <code>GetterMapping</code> capable of inserting an ascii stream (InputStream)
 * (from a Butterfly Persistence AsciiStream instance) into a <code>PreparedStatement</code>.
 *
 * @author Jakob Jenkov, Jenkov Development
 */

public class AsciiStreamGetterMapping extends GetterMapping{

    protected void insertObjectDo(Object value, PreparedStatement statement, int index) throws SQLException {
        statement.setAsciiStream(index, ((AsciiStream) value).getInputStream(), ((AsciiStream) value).getLength());
    }

}
