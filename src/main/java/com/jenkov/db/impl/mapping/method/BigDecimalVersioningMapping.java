package com.jenkov.db.impl.mapping.method;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.jenkov.db.itf.PersistenceException;
import com.jenkov.db.itf.mapping.IObjectMapping;
import com.jenkov.db.itf.mapping.ISetterMapping;

/**
 * 
 * @author takezoux2
 *
 */
public class BigDecimalVersioningMapping extends VersioningMapping{

    protected void insertObjectDo(Object value, PreparedStatement statement, int index) throws SQLException {
        statement.setBigDecimal(index, ((BigDecimal) value).add(BigDecimal.ONE));
    }
	@Override
	protected void compareDo(Object value, PreparedStatement statement,
			int index) throws SQLException {
		statement.setBigDecimal(index, (BigDecimal) value);
		
	}

	@Override
	public void incrementVersion(IObjectMapping mapping, Object target) throws PersistenceException {
		ISetterMapping sMap = mapping.getSetterMapping(getColumnName());
    	if(sMap == null){
    		throw new PersistenceException("There is no versioning setter.");
    	}
    	Object value;
		try {
			value = getObjectMethod().invoke(target);
	    	if(value == null){
	    		sMap.getObjectMethod().invoke(target, BigDecimal.ONE);
	    	}else{
	    		sMap.getObjectMethod().invoke(target, ((BigDecimal)value).add(BigDecimal.ONE));
	    	}
		} catch (IllegalAccessException e) {
            throw new PersistenceException("Could not set value of type  "
                    + getObjectMethod().getReturnType() + "  for field  "
                    + getColumnName() + "  from object into PreparedStatement", e);
		} catch (InvocationTargetException e) {
            throw new PersistenceException("Could not insert value of type  "
                    + getObjectMethod().getReturnType() + "  for field  "
                    + getColumnName() + "  from object into PreparedStatement", e);
		}
		
	}
}
