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

import com.jenkov.db.itf.PersistenceException;
import com.jenkov.db.itf.IPersistenceConfiguration;
import com.jenkov.db.itf.mapping.*;
import com.jenkov.db.util.ClassUtil;

import java.lang.reflect.Method;
import java.sql.Connection;

/**
 * @author Jakob Jenkov - Copyright 2004-2006 Jenkov Development
 */
public class CustomObjectMapperAnnotationBased{

    protected IObjectMappingFactory factory = null;

    public CustomObjectMapperAnnotationBased(IObjectMappingFactory factory) {
        this.factory = factory;
    }

    public IObjectMapping getObjectMapping(Object objectMappingKey, IPersistenceConfiguration configuration,
                                           Connection connection) throws PersistenceException {
        if(!(objectMappingKey instanceof ObjectMappingKey || objectMappingKey instanceof Class)) return null;
        Class targetClass = objectMappingKey instanceof Class? (Class) objectMappingKey : ((ObjectMappingKey) objectMappingKey).getClass() ;

        AClassMapping classMapping = (AClassMapping) targetClass.getAnnotation(AClassMapping.class);
        if(classMapping != null && "manual".equals(classMapping.mappingMode())){
            IObjectMapping mapping = this.factory.createObjectMapping();
            mapping.setObjectClass(targetClass);
            if(isSet(classMapping.tableName())) mapping.setTableName(classMapping.tableName());
            createOrModifyMethodMappings(mapping, configuration, connection);
            return mapping;
        }
        return null;
    }

    public String getTableName(Object objectMappingKey) throws PersistenceException {
        if(!(objectMappingKey instanceof ObjectMappingKey || objectMappingKey instanceof Class)) return null;
        Class targetClass = objectMappingKey instanceof Class? (Class) objectMappingKey : ((ObjectMappingKey) objectMappingKey).getObjectClass() ;

        AClassMapping classMapping = (AClassMapping) targetClass.getAnnotation(AClassMapping.class);
        if(classMapping != null && isSet(classMapping.tableName())){
            return classMapping.tableName();
        }

        return null;
    }

    public void modify(Object objectMappingKey, IObjectMapping mapping, IPersistenceConfiguration configuration,
                       Connection connection) throws PersistenceException {
        createOrModifyMethodMappings(mapping, configuration, connection);
    }

    public void createOrModifyMethodMappings(IObjectMapping mapping, IPersistenceConfiguration configuration,
                                             Connection connection) throws PersistenceException {
        Method[] methods = mapping.getObjectClass().getMethods();
        for(Method method : methods){
            if (ClassUtil.isGetter(method)) {
            	AVersioning versioningAnnotation = (AVersioning) method.getAnnotation(AVersioning.class);
            	if(versioningAnnotation != null){
            		IGetterMapping getterMapping = mapping.getGetterMapping(method);
            		// if getter mapping have been registered,remove it.
                    if(mapping.getGetterMapping(method) != null){
            			mapping.removeGetterMapping(method);
                    }
            		if(getterMapping == null){
            			getterMapping = this.factory.createVersioningMapping(method,null,false);
            		}else{
            			getterMapping = this.factory.convertToVersioning(getterMapping);
            		}
                    if(isSet(versioningAnnotation.columnName())) {
                        getterMapping.setColumnName (versioningAnnotation.columnName());
                    }
                    getterMapping.setObjectMethod(method);
                    if(isSet(versioningAnnotation.columnType())){
                        getterMapping.setColumnType   (translateColumnType(method, versioningAnnotation.columnType()));
                    }

                    mapping.addGetterMapping(getterMapping);
            		continue;
            	}
            	
                AGetterMapping getterAnnotation = (AGetterMapping) method.getAnnotation(AGetterMapping.class);
                if(getterAnnotation != null){
                    IGetterMapping getterMapping = mapping.getGetterMapping(method);
                    if(getterMapping == null){
                        getterMapping = this.factory.createGetterMapping(method, null, false);
                    }
                    if(isSet(getterAnnotation.columnName())) {
                        getterMapping.setColumnName (getterAnnotation.columnName());
                    }
                    getterMapping.setObjectMethod(method);
                    getterMapping.setAutoGenerated(getterAnnotation.databaseGenerated());
                    getterMapping.setTableMapped  (getterAnnotation.includeInWrites());
                    if(isSet(getterAnnotation.columnType())){
                        getterMapping.setColumnType   (translateColumnType(method, getterAnnotation.columnType()));
                    }
                    if(mapping.getGetterMapping(method) == null){
                        mapping.addGetterMapping(getterMapping);
                    }
                }
            } else if (ClassUtil.isSetter(method)) {
                ASetterMapping setterAnnotation = (ASetterMapping) method.getAnnotation(ASetterMapping.class);
                if(setterAnnotation != null){
                    ISetterMapping setterMapping = mapping.getSetterMapping(method);
                    if(setterMapping == null){
                        setterMapping = this.factory.createSetterMapping(method, null, false);
                    }
                    if(isSet(setterAnnotation.columnName())){
                        setterMapping.setColumnName(setterAnnotation.columnName());
                    }
                    setterMapping.setObjectMethod(method);
                    if(isSet(setterAnnotation.columnType())){
                        setterMapping.setColumnType(translateColumnType(method, setterAnnotation.columnType()));
                    }
                    if(mapping.getSetterMapping(method) == null){
                        mapping.addSetterMapping(setterMapping);
                    }
                }
            }
        }
    }

    private int translateColumnType(Method method, String columnType) {
        if("number".equals(columnType)){
            return java.sql.Types.NUMERIC;
        }
        if("string".equals(columnType)){
            return java.sql.Types.VARCHAR;
        }
        if("date".equals(columnType)){
            return java.sql.Types.TIMESTAMP;
        }
        if("binary".equals(columnType)){
            return java.sql.Types.BLOB;
        }
        throw new IllegalArgumentException("Annotation mapping error in method: " + method.getClass().getName()
                + "." + method.getName() + "(). 'columnType' mapping was " + columnType +
                ". The annotation 'columnType' must have one of the values: number, string, date, binary");
    }

    protected boolean isSet(String value){
        return !"".equals(value);
    }
}
