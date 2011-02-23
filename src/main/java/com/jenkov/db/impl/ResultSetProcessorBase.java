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

import com.jenkov.db.itf.IResultSetProcessor;
import com.jenkov.db.itf.PersistenceException;
import com.jenkov.db.itf.IDaos;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Jakob Jenkov - Copyright 2005 Jenkov Development
 */
public class ResultSetProcessorBase implements IResultSetProcessor {

    protected Object result = null;

    public void init(ResultSet result, IDaos daos) throws SQLException, PersistenceException {
    }

    public void process(ResultSet result, IDaos daos) throws SQLException, PersistenceException {
    }

    protected void setResult(Object object){
        this.result = object;
    }

    public Object getResult() throws PersistenceException {
        return this.result;
    }
}
