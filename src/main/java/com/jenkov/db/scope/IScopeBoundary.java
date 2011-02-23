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

/**
 * Represents the boundaries of a scope. A scope is started,
 * the scope action executed, and the scope ended. The object
 * returned is the return value of the scope action.
 *
 * @author Jakob Jenkov - Copyright 2005 Jenkov Development
 */
public interface IScopeBoundary {

    /**
     * Executes the given action within this scope.
     * @param action The action to be executed within this scope.
     * @return The return value of the scope action's inScope() method.
     */
    public Object scope(IScopeAction action);
}
