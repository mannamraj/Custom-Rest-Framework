package com.raja.rest.common.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.common.reflect.ClassPath;
import com.raja.rest.common.Loader;
import com.raja.rest.exception.FramerWorkException;

public class ClassLoader implements Loader<Class<?>,String>{

	@Override
	public List<Class<?>> load(String source) {
		
		List<Class<?>> classList =  null;

    	if(source != null && !"".equalsIgnoreCase(source.trim())){
    		
    		classList =  new ArrayList<Class<?>>();
    		
    		 final java.lang.ClassLoader loader = Thread.currentThread().getContextClassLoader();
    		 
    	        try {
    	            ClassPath classpath = ClassPath.from(loader); 
    	            
    	            for (ClassPath.ClassInfo classInfo : classpath.getTopLevelClasses(source)) {
    	            	
    	                if(!classInfo.getSimpleName().endsWith("_")){
    
    	                	classList.add(classInfo.load());
    	                	
    	                }
    	            }
    	        } catch (IOException e) {
    	           throw new FramerWorkException(e,"Loader");
    	        }
    		
    	}
       
    	return classList;
    		
	}

}
