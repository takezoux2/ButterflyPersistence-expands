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



package com.jenkov.db.impl.filter;

import com.jenkov.db.itf.IReadFilter;
import com.jenkov.db.itf.PersistenceException;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This read filter implementation accepts all records as the object(s) read. This filter
 * is used as default filter in case null is passed as filter to a read method taking a filter.
 * This means null = no filtering = all records accepted.
 */
public class AcceptAllReadFilter implements IReadFilter{

    public static final IReadFilter ACCEPT_ALL_FILTER = new AcceptAllReadFilter();

    public void     init(ResultSet result) throws SQLException, PersistenceException    {/* do nothing */ }
    public boolean  accept(ResultSet result) throws SQLException, PersistenceException  { return true;    }
    public boolean  acceptMore()                                                        { return true;    }

    public void acceptedByAllFilters(boolean wasAcceptedByAllFilters)                   {/* do nothing */ }

    public void     clear()                                                             {/* do nothing */ }

}
