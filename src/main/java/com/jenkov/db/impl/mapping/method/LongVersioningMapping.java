package com.jenkov.db.impl.mapping.method;

import java.lang.reflect.InvocationTargetException;
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
public class LongVersioningMapping extends VersioningMapping{

    protected void insertObjectDo(Object value, PreparedStatement statement, int index) throws SQLException {
        if(value != null){
            statement.setLong(index, ((Long) value).longValue() + 1);
        } else {
            statement.setLong(index, 1);
        }
    }
	@Override
	protected void compareDo(Object value, PreparedStatement statement,
			int index) throws SQLException {
		if(value != null){
            statement.setLong(index, ((Long) value).longValue());
        } else {
            statement.setLong(index, 0);
        }
		
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
	    		sMap.getObjectMethod().invoke(target, new Long(1));
	    	}else{
	    		long v = (Long)value;
	    		sMap.getObjectMethod().invoke(target, new Long(v + 1));
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
