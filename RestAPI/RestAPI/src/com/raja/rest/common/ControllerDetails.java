package com.raja.rest.common;

import java.lang.reflect.Method;
import java.util.List;

import com.raja.rest.util.RestUtil;

public class ControllerDetails {

    String name;

    Class<?> clazz;

    String methodName;

    Method method;
    
    String servicePath;
    
    String contentType;
    
    List<MethodArgument> methodArguments ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

	public List<MethodArgument> getMethodArguments() {
		return methodArguments;
	}

	public void setMethodArguments(List<MethodArgument> methodArguments) {
		this.methodArguments = methodArguments;
	}

	public String getServicePath() {
		return servicePath;
	}

	public void setServicePath(String servicePath) {
		this.servicePath = servicePath;
	}
    
    public Object[] getArguments(List<MethodArgument> methodArguments){
    	
    	Object[] objects = null;
    	
    	if(RestUtil.isNotEmpty(methodArguments)){
    		
    		objects = new Object[methodArguments.size()];
    		
    		for(int i = 0 ; i < methodArguments.size() ; i++){
    			
    			objects[i] = methodArguments.get(i).getValue();
    		}
    		
    	}
    	
    	return objects;
    }

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

}
