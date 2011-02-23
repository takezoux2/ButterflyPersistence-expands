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

import com.jenkov.db.impl.mapping.KeyValue;
import com.jenkov.db.itf.IObjectDao;
import com.jenkov.db.itf.PersistenceException;
import com.jenkov.db.itf.mapping.IKeyValue;
import com.jenkov.db.itf.mapping.IObjectMapping;

import java.sql.ResultSet;

/**
 * @author Jakob Jenkov - Copyright 2004-2006 Jenkov Development
 */
public class ObjectDaoCaching {

    protected ObjectCache cache     = new ObjectCache();
    protected IObjectDao  objectDao = null;

    public ObjectDaoCaching(IObjectDao objectDao) {
        this.objectDao = objectDao;
    }

    public Object read(Object objectMappingKey, ResultSet result) throws PersistenceException {
        IObjectMapping objectMapping = this.objectDao.getConfiguration().getObjectMappingCache().getObjectMapping(objectMappingKey);
        IKeyValue keyValue = objectMapping.getPrimaryKeyValueForRecord(result, new KeyValue());
        Object object = this.cache.get(objectMappingKey, keyValue);
        if(object == null){
            object = this.objectDao.read(objectMappingKey, result);
            if(object != null)
            this.cache.store(objectMappingKey, keyValue, object);
        }
        return object;
    }


}
