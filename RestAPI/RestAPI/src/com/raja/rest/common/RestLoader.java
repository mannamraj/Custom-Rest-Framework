package com.raja.rest.common;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.reflect.ClassPath;
import com.raja.rest.annotations.Path;
import com.raja.rest.annotations.Request;
import com.raja.rest.annotations.RestInputBody;
import com.raja.rest.annotations.RestService;
import com.raja.rest.annotations.RestServiceMapper;
import com.raja.rest.exception.FramerWorkException;

public class RestLoader {
	
    public static Map<String,List<ControllerDetails>> getControllerMap(String path) throws  Exception{

        List<Class<?>> classList = getClassOfPackage(path);

        //System.out.println(classList);

        List<Class<?>> controllerClasses = new ArrayList<>();

        Map<String,List<ControllerDetails>> controllerDetailsMap = new HashMap<>();


        for (Class<?> clazz: classList) {

            //System.out.println(clazz.getAnnotations());

            ControllerDetails controllerDetails = null;


            Annotation annotation = clazz.getAnnotation(RestService.class);
                /*System.out.println(annotation.annotationType().getName());*/

            if(annotation != null ) {

                RestService restService = (RestService) annotation;

                String endPoint = restService.endPoint();

                controllerClasses.add(clazz);

                Method[] methods = clazz.getMethods();
                
                List<MethodArgument> methodArguments = null;

                for (Method method:methods) {

                    RestServiceMapper restServiceMapper = method.getAnnotation(RestServiceMapper.class);

                    if(restServiceMapper != null){
                    	
                    	methodArguments = new ArrayList<>();
                    	
                        String serviceName  = restServiceMapper.serviceName();

                        controllerDetails = new ControllerDetails();

                        controllerDetails.setClazz(clazz);

                        controllerDetails.setName(endPoint);

                        controllerDetails.setMethodName(serviceName);

                        controllerDetails.setMethod(method);
                        
                        controllerDetails.setServicePath("/"+endPoint+"/"+serviceName);
                        
                        if(!controllerDetailsMap.containsKey(endPoint)){
                        	
                        	controllerDetailsMap.put(endPoint,new ArrayList<ControllerDetails>());
                        	
                        }

                        controllerDetailsMap.get(endPoint).add(controllerDetails);
                                                
                    	Parameter[] parameters =  method.getParameters();
                    	
                    	MethodArgument methodArgument = null;
                    	
                    	if(parameters != null && parameters.length > 0){
                    		
                    		for(Parameter parameter : parameters){
                    			
                    			methodArgument = new MethodArgument();
                    			
                    			Request requestParameter = parameter.getAnnotation(Request.class);
                    			
                    			Path pathParameter = parameter.getAnnotation(Path.class);

                    			RestInputBody requestBody = parameter.getAnnotation(RestInputBody.class);
                    			
                    			if(requestParameter != null){
                    				
                    				methodArgument.setParamType("requestParameter");
                    				
                    				methodArgument.setName(requestParameter.name());
                    			}
                    			
								if(pathParameter != null){
									
                    				methodArgument.setParamType("pathParameter");
                    				
                    				methodArgument.setName(pathParameter.name());

								                    				
								}
								
								if(requestBody != null){
									
                    				methodArgument.setParamType("requestBody");
                    				
                    				methodArgument.setClazz(parameter.getType());
                    													
								}
								
								methodArguments.add(methodArgument);
                    			
                    		}
                    	}
                    	
                    	controllerDetails.setMethodArguments(methodArguments);

                    }

                }
            }


        }

        System.out.println(controllerClasses);

        return controllerDetailsMap;
    }

    
    public static List<Class<?>>  getClassOfPackage(String packageName) {
    	
    	List<Class<?>> classList =  null;

    	if(packageName != null && !"".equalsIgnoreCase(packageName.trim())){
    		
    		classList =  new ArrayList<Class<?>>();
    		
    		 final java.lang.ClassLoader loader = Thread.currentThread().getContextClassLoader();
    	        try {
    	            ClassPath classpath = ClassPath.from(loader); 
    	            
    	            for (ClassPath.ClassInfo classInfo : classpath.getTopLevelClasses(packageName)) {
    	            	
    	                if(!classInfo.getSimpleName().endsWith("_")){
    
    	                	classList.add(classInfo.load());
    	                	
    	                }
    	            }
    	        } catch (IOException e) {
    	            throw new FramerWorkException(e,"Package Scanning Exception");
    	        }
    		
    	}
       
    	return classList;

    }

}

