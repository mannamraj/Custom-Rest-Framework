package com.raja.rest.common.impl;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.common.reflect.ClassPath;
import com.raja.rest.common.Loader;
import com.raja.rest.exception.FramerWorkException;

public class ClassLoader implements Loader<Class<?>,String>{
	
	private Class<? extends Annotation> annotationClass ;

	@Override
	public List<Class<?>> load(String source) {
		
		List<Class<?>> classList =  null;

    	if(source != null && !"".equalsIgnoreCase(source.trim())){
    		
    		classList =  new ArrayList<Class<?>>();
    		
    		 final java.lang.ClassLoader loader = Thread.currentThread().getContextClassLoader();
    		 
    	        try {
    	            ClassPath classpath = ClassPath.from(loader); 
    	            
    	            Set<ClassPath.ClassInfo> classInfoSet = classpath.getTopLevelClasses(source);
    	            
    	            for (ClassPath.ClassInfo classInfo : classInfoSet) {
    	            	
    	            	Class<?> clazz = classInfo.load();
    	            	
    	                if(thisCanLoad(clazz)){
    
    	                	classList.add(clazz);
    	                	
    	                }
    	            }
    	        } catch (IOException e) {
    	           throw new FramerWorkException(e,"Loader");
    	        }
    		
    	}
       
    	return classList;
    		
	}
	
	private boolean thisCanLoad(Class<?> clazz) {

		boolean flag = true;
		
		if(clazz == null) return false;
		
		if(!isAnnotationPresent(clazz)) return false;
		
		if(clazz.isAnnotation() || clazz.isInterface()) {
			
			flag = false;
		}
		return flag;
	}

	private boolean isAnnotationPresent(Class<?> clazz){

		if(getAnnotationClass() == null) {
			return true;
		}			
		
		return clazz.isAnnotationPresent(annotationClass); 
	}
	public Class<?> getAnnotationClass() {
		return annotationClass;
	}

	public ClassLoader setAnnotationClass(Class<? extends Annotation> annotationClass) {
		this.annotationClass = annotationClass;
		return this;
	}

	
	
}
