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

import com.jenkov.db.itf.mapping.IKeyValue;

import java.util.Map;
import java.util.HashMap;

/**
 *
 * Copyright Jenkov Aps
 */
public class ObjectCache {

    protected Map<Object, Map<IKeyValue, Object>> map = new HashMap<Object, Map<IKeyValue, Object>>();


    public synchronized Object get(Object objectMappingKey, IKeyValue pkKeyValue){
        Map<IKeyValue, Object> objectMap = map.get(objectMappingKey);
        if(objectMap != null){
            return objectMap.get(pkKeyValue);
        }
        return null;
    }

    public synchronized void store(Object objectMappingKey, IKeyValue pkKeyValue, Object object){
        Map<IKeyValue, Object> objectMap = new HashMap<IKeyValue, Object>();
        objectMap.put(pkKeyValue, object);
        this.map.put(objectMappingKey, objectMap);
    }

    public synchronized void clear(){
        for(Map objectMap : this.map.values()){
            objectMap.clear();
        }
        this.map.clear();
    }
}
