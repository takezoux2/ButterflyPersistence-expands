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



package com.jenkov.db.scope;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * An implementation of the IScopeBoundary that can mark
 * the boundaries of a transaction scope. Users of the scoping features
 * will not use this class directly. It is used by the ScopeFactory class.
 *
 * @author Jakob Jenkov - Copyright 2005 Jenkov Development
 */
public class TransactionScope implements IScopeBoundary, IScopeAction, InvocationHandler {

    protected ScopingDataSource dataSource  = null;
    protected Object            scopeTarget = null;

    public TransactionScope(ScopingDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public TransactionScope(ScopingDataSource dataSource, Object scopeTarget) {
        this.dataSource = dataSource;
        this.scopeTarget = scopeTarget;
    }

    public Object scope(){
        return scope(this);
    }

    public Object scope(IScopeAction scopeAction){
        Object returnValue = null;
        try{
            this.dataSource.beginTransactionScope();
            returnValue =  scopeAction.inScope();
            this.dataSource.endTransactionScope();
        } catch(Throwable t){
            this.dataSource.abortTransactionScope(t);
        }
        return returnValue;
    }

    ///CLOVER:OFF
    public Object inScope() throws Throwable{
        return null;
    }
    ///CLOVER:ON

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(method.getName().equals("toString") || method.getName().equals("hashCode") || method.getName().equals("getClass")){
            return method.invoke(this.scopeTarget, args);
        }
        
        Object returnValue = null;
        try{
            this.dataSource.beginTransactionScope();
            returnValue =  method.invoke(this.scopeTarget, args);
            this.dataSource.endTransactionScope();
        } catch(Throwable t){
            this.dataSource.abortTransactionScope(t);
        }
        return returnValue;
    }
}
