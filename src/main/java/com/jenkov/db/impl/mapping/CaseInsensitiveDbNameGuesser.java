package com.jenkov.db.impl.mapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 
 * in windows enviroment, default table name guessing 
 * 
 * 
 * @author takezoux2
 *
 */
public class CaseInsensitiveDbNameGuesser extends DbNameGuesser{
	
	@Override
	public Collection getPossibleTableNames(Class objectClass) {
		List<String> names = new ArrayList<String>(4);
		String simpleName = objectClass.getSimpleName();
		names.add(simpleName.toLowerCase());
		names.add(simpleName.toLowerCase() + "s");

		StringBuffer buffer = new StringBuffer(simpleName.length() + 3);
		char[] chars = simpleName.toCharArray();
		buffer.append(Character.toLowerCase(chars[0]));
		for(int i = 1;i < chars.length;i++)
		{
			if(Character.isUpperCase(chars[i])){
				buffer.append("_");
			}
			buffer.append(Character.toLowerCase(chars[i]));
		}
		names.add(buffer.toString());
		names.add(buffer.toString() + "s");

		return names;  
	}

}
