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



package com.jenkov.db.itf.mapping;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

/**
 * @author Jakob Jenkov - Copyright 2004-2006 Jenkov Development
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ASetterMapping {
//    String  mappingId()           default ""; //useless
    String  columnName()          default "";
    String  columnType()          default "";    // number, string, date, binary

    //is this ever necessary to set?
    //setters don't care where the data comes from... table or query.
//    boolean columnExistsInTable() default true; // true = column is from table, false = column is from a view/query only.

}
