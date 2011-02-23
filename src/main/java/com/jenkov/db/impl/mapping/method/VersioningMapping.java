package com.jenkov.db.impl.mapping.method;

import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.jenkov.db.itf.PersistenceException;
import com.jenkov.db.itf.mapping.IVersioningMapping;

/**
 * 
 * @author takezoux2
 *
 */
public abstract class VersioningMapping extends GetterMapping implements IVersioningMapping{

	@Override
	public void compareVersioning(Object target, PreparedStatement statement,
			int index) throws PersistenceException {
		try {
			compareDo(getObjectMethod().invoke(target, null), statement, index);
        } catch (SQLException e) {
            throw new PersistenceException("Could not insert value of type  "
                    + getObjectMethod().getReturnType() + "  for field  "
                    + getColumnName() + "  from object into PreparedStatement", e);
        } catch (InvocationTargetException e) {
            throw new PersistenceException("Could not insert value of type  "
                    + getObjectMethod().getReturnType() + "  for field  "
                    + getColumnName() + "  from object into PreparedStatement", e);
        } catch (IllegalAccessException e) {
            throw new PersistenceException("Could not insert value of type  "
                    + getObjectMethod().getReturnType() + "  for field  "
                    + getColumnName() + "  from object into PreparedStatement", e);
        }
		
		
	}
	
	protected abstract void compareDo(Object value , PreparedStatement statement,int index)throws SQLException;
	
    

}
