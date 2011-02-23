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



/**
 * User: Administrator
 */
package com.jenkov.db.util;

import java.util.List;
import java.util.Collection;
import java.util.Iterator;
import java.util.ArrayList;

public class CollectionUtils {


    /**
     * Converts the given collection to a <code>java.util.List</code>.
     * @param collection The collection to convert to a list.
     * @return A <code>java.util.List</code> containing all of the elements from the
     *         supplied collection.
     */
    public static List toList(Collection collection){
        if(collection instanceof List) return (List) collection;

        List list = new ArrayList();
        Iterator iterator = collection.iterator();
        while(iterator.hasNext()){
            list.add(iterator.next());
        }
        return list;
    }

    public static boolean areEqual(Collection collection1, Collection collection2){
        if(collection1.size() != collection2.size()) return false;

        Iterator iterator = collection1.iterator();
        while(iterator.hasNext()){
            if(! collection2.contains(iterator.next())) return false;
        }
        return true;
    }
}
