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

import com.jenkov.db.itf.ISqlCache;

import java.util.Map;
import java.util.HashMap;

public class SqlCache implements ISqlCache {

    protected Map sqlStatements = new HashMap();

    public boolean containsStatement(Object mappingKey) {
        return this.sqlStatements.containsKey(mappingKey);
    }

    public String getStatement(Object mappingKey) {
        return (String) this.sqlStatements.get(mappingKey);
    }

    public void storeStatement(Object mappingKey, String insertStatement) {
        this.sqlStatements.put(mappingKey, insertStatement);
    }

    public void removeStatement(Object mappingKey) {
        this.sqlStatements.remove(mappingKey);
    }

    public void clear() {
        this.sqlStatements.clear();
    }

    public int size() {
        return this.sqlStatements.size();
    }
}
