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

import com.jenkov.db.itf.mapping.IObjectMappingCache;
import com.jenkov.db.itf.mapping.IObjectMapping;

import java.util.*;

public class ObjectMappingCache implements IObjectMappingCache{

    protected Map objectMappings = new HashMap();



    public boolean containsObjectMapping(Object mappingKey){
        return this.objectMappings.containsKey(mappingKey);
    }

    public IObjectMapping getObjectMapping(Object mappingKey) {
        return (IObjectMapping) this.objectMappings.get(mappingKey);
    }

    public void storeObjectMapping(Object mappingKey, IObjectMapping mapping) {
        this.objectMappings.put(mappingKey, mapping);
    }

    public void removeObjectMapping(Object mappingKey) {
        this.objectMappings.remove(mappingKey);
    }

    public void clear() {
        this.objectMappings.clear();
    }

    public int size() {
        return this.objectMappings.size();
    }

}
