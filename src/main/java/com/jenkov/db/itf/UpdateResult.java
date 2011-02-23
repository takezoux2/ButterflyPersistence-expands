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



package com.jenkov.db.itf;

import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;

/**
 * Represents the result of an SQL update. The result contains
 * the number of affected records and the generated keys as
 * returned by the JDBC driver, if any.
 *
 * @author Jakob Jenkov - Copyright 2005 Jenkov Development
 */
public class UpdateResult {

    protected int[] affectedRecords = null;
    protected List  generatedKeys   = new ArrayList();

    /**
     * Returns an array of affected record counts. The reason
     * it's an array is so that it can contain one record update
     * count per SQL query in a batch update. For single update queries,
     * only the first element is filled in.
     * @return
     */
    public int[] getAffectedRecords() {
        return affectedRecords;
    }

    /**
     * Used internally by Butterfly Persistence.
     * @param affectedRecords
     */
    public void setAffectedRecords(int[] affectedRecords) {
        this.affectedRecords = affectedRecords;
    }

    /**
     * Returns a List of all generated keys in this update.
     *
     * @return A List of all generated keys in this update.
     */
    public List getGeneratedKeys() {
        return generatedKeys;
    }

    /**
     * Used internally by Butterfly Persistence.
     * @param key
     */
    public void addGeneratedKey(Object key){
        this.generatedKeys.add(key);
    }

    /**
     * Returns a generated key as a long. If no key was generated an IndexOutOfBoundsException is thrown.
     *
     * @param index The index of the generated key as long. Most often only one key is generated which has index 0.
     * @return A generated key as a Long object.
     */
    public long getGeneratedKeyAsLong(int index){
        Object key = getGeneratedKeys().get(index);
        if(key instanceof Long) return ((Long) key).longValue();
        else if(key instanceof BigDecimal) return ((BigDecimal) key).longValue();
        return Long.parseLong(key.toString());
    }

    /**
     * Returns the latest generated key as long. A shortcut for writing
     * getGeneratedKeyAsLong(0). If no key was generated an IndexOutOfBoundsException is thrown.  
     *
     * @return The latest generated key as long.
     */
    public long getLastGeneratedKeyAsLong() {
        return getGeneratedKeyAsLong(getGeneratedKeys().size() - 1);
    }


    /**
     * Returns a generated key as a BigDecimal. If no key was generated an IndexOutOfBoundsException is thrown.
     *
     * @param index The index of the generated key as BigDecimal. Most often only one key is generated which has index 0.
     * @return A generated key as a BigDecimal object.
     */
    public BigDecimal getGeneratedKeyAsBigDecimal(int index){
        Object key = getGeneratedKeys().get(index);
        if(key instanceof BigDecimal) return (BigDecimal) key;
        else if(key instanceof Long) return new BigDecimal(((Long) key).longValue());
        return new BigDecimal(key.toString());
    }

    /**
     * Returns the latest generated key as BigDecimal. A shortcut for writing
     * getGeneratedKeyAsLong(0). If no key was generated an IndexOutOfBoundsException is thrown.
     *
     * @return The latest generated key as long.
     */
    public BigDecimal getLastGeneratedKeyAsBigDecimal() {
        return getGeneratedKeyAsBigDecimal(getGeneratedKeys().size() - 1);
    }


}
