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



package com.jenkov.db.impl.init;

import com.jenkov.db.itf.init.IDatabaseInitialization;
import com.jenkov.db.itf.IDaos;
import com.jenkov.db.itf.PersistenceException;

/**

 */
public class SqlInitialization implements IDatabaseInitialization {

    protected int    version = 0;
    protected String sql     = null;

    public SqlInitialization(int version, String sql) {
        this.version = version;
        this.sql = sql;
    }

    public int getVersion() {
        return this.version;
    }

    public void execute(IDaos daos) throws Exception {
        daos.getJdbcDao().update(this.sql);
    }
}
