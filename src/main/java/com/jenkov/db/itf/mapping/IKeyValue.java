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

import java.util.Map;

/**
 * This interface represents a concrete compound key. You can add values
 * for each column in the key.
 *
 * @author Jakob Jenkov - Copyright 2005 Jenkov Development
 */
public interface IKeyValue {

    /**
     * Adds a value to a column of this key.
     * @param column The column to specify the value of.
     * @param value  The column value.
     */
    public IKeyValue addColumnValue(String column, Object value);

    /**
     * Removes a column value from this key value.
     * @param column The column to remove the value of.
     */
    public IKeyValue removeColumnValue(String column);

    /**
     * Returns the value for the given column. If there is no
     * value for that column null is returned.
     * @param column The column to return the value of.
     * @return The column value if present. Otherwise null.
     */
    public Object getColumnValue(String column);

    /**
     * Returns a map of all column values in this key value.
     * @return A map of all column values in this key value.
     */
    public Map  getColumnValues();

}
