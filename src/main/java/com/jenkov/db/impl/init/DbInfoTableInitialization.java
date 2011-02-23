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
public class DbInfoTableInitialization implements IDatabaseInitialization {

    public int getVersion() {
        return 0;
    }

    public void execute(IDaos daos) throws PersistenceException {
        String dbInfoSql1 = "create table db_info(name varchar(255), value varchar(255))";
        daos.getJdbcDao().update(dbInfoSql1);

        String versionRecordSql = "insert into db_info(name, value) values('version', '0')";
        daos.getJdbcDao().update(versionRecordSql);
    }
}
