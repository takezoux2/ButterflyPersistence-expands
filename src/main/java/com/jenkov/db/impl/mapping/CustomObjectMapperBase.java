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



package com.jenkov.db.impl.mapping;

import com.jenkov.db.itf.mapping.ICustomObjectMapper;
import com.jenkov.db.itf.mapping.IObjectMapping;
import com.jenkov.db.itf.PersistenceException;

/**
 * An abstract base class for easy implementation of custom object mappers. Extend
 * this class and override the methods you need.
 *
 * @author Jakob Jenkov - Copyright 2005 Jenkov Development
 */


///CLOVER:OFF
public abstract class CustomObjectMapperBase implements ICustomObjectMapper{
    public IObjectMapping getObjectMapping(Object objectMappingKey) throws PersistenceException {
        return null;
    }

    public String getTableName(Object objectMappingKey) throws PersistenceException {
        return null;
    }

    public void modify(Object objectMappingKey, IObjectMapping mapping) throws PersistenceException {
    }
}
///CLOVER:ON