package com.jenkov.db.itf.mapping;

import java.sql.PreparedStatement;

import com.jenkov.db.itf.PersistenceException;

public interface IVersioningMapping extends IGetterMapping{
	
	
	public void compareVersioning(Object target, PreparedStatement statement, int index) throws PersistenceException;
	
	public void incrementVersion(IObjectMapping mapping,Object target) throws PersistenceException ;

}
