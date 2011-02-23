package com.jenkov.db.impl.mapping.method;

import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.jenkov.db.itf.PersistenceException;
import com.jenkov.db.itf.mapping.IObjectMapping;
import com.jenkov.db.itf.mapping.ISetterMapping;
import com.jenkov.db.itf.mapping.IVersioningMapping;

/**
 * 
 * @author takezoux2
 *
 */
public class IntVersioningMapping extends VersioningMapping{

    protected void insertObjectDo(Object value, PreparedStatement statement, int index) throws SQLException {
        if(value != null){
            statement.setInt(index, ((Integer) value).intValue() + 1); // increment
        } else {
            statement.setInt(index,1);
        }
    }
	@Override
	protected void compareDo(Object value, PreparedStatement statement,
			int index) throws SQLException {
		if(value != null){
            statement.setInt(index, ((Integer) value).intValue());
        } else {
            statement.setInt(index, 0);
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
	    		sMap.getObjectMethod().invoke(target, new Integer(1));
	    	}else{
	    		int v = (Integer)value;
	    		sMap.getObjectMethod().invoke(target, new Integer(v + 1));
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
