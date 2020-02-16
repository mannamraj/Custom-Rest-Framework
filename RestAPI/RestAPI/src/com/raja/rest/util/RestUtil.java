package com.raja.rest.util;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.raja.rest.common.ControllerDetails;
import com.raja.rest.common.MethodArgument;
/**
 * 
 * 
 * This Rest Utility Class 
 * 
 * 
 * @author albert.mannam
 *
 */

public class RestUtil {
	
	public final static String REQUEST_PARAM = "requestParameter";

	public final static String PATH_PARAM = "pathParameter";

	public final static String REST_BODY = "requestBody";
		
	public static Object[] getArgumetns(HttpServletRequest request ,ControllerDetails controllerDetails){
		
		List<MethodArgument> arguments = controllerDetails.getMethodArguments();
		
		Object[] argumentObjectArray = null;
		
		if(isNotEmpty(arguments)){
			
			Map<String,Object> pathParamMap = getPathParamMap(request.getPathInfo(),controllerDetails.getServicePath());
			
			argumentObjectArray = new Object[arguments.size()];
			
			int i = 0;
			
			for(MethodArgument argument : arguments){
			
				if(REQUEST_PARAM.equalsIgnoreCase(argument.getParamType())){
					
					argument.setValue(request.getParameter(argument.getName()));
					
				}else if(PATH_PARAM.equalsIgnoreCase(argument.getParamType())){
					
					argument.setValue(pathParamMap.get(argument.getName()));
					
				}else if(REST_BODY.equalsIgnoreCase(argument.getParamType())){
					
					Gson gson = new Gson();
					
					argument.setValue(gson.fromJson(getRequestBody(request), argument.getClazz()));
					
				}
				
				argumentObjectArray[i] = argument.getValue();
				
				i++;
				
			}
			
		}
		
		return argumentObjectArray;
		
	}

	private static Map<String, Object> getPathParamMap(String pathInfo, String servicePath) {
		
		Map<String,Object> pathParamMap  = new HashMap<>();
		
		if(servicePath.contains("{")){
			
			String pathSourceArray[] = pathInfo.split("/");
			
			String pathTargetArray[] = servicePath.split("/");
			
			
			for(int i=0;i<pathSourceArray.length;i++){
				
				String pathSource = pathSourceArray[i];
				
				String pathTarget = pathTargetArray[i];

				
				if(pathTarget.contains("{")){
					
					pathParamMap.put(pathTarget.substring(1,pathTarget.length() -1), pathSource);

				}
			}
		}
		
		return pathParamMap;
	}

	private static String getRequestBody(HttpServletRequest request){
		
		StringBuffer requestBody = new StringBuffer();
		
		 try {
			 
			    BufferedReader reader = request.getReader();
			    
			    String line;
			    
			    while ((line = reader.readLine()) != null){
			    	
			    	requestBody.append(line);
			    	
			    }
			    
			  } catch (Exception e) {
				  /*report an error*/ 				  
				  
			  }
		 return requestBody.toString();
		 
	}
	
	public static boolean isNotEmpty(List<?> list){
		return list != null &&  list.size() >0;
	}
	
	
	public static boolean compare(String pathSource,String pathTarget){
		
		boolean flag = true ;
		
		String pathSourceArray[] = pathSource.split("/");
		
		String pathTargetArray[] = pathTarget.split("/");
		
		if(pathSource != null && pathTarget != null &&
				pathSource.split("/").length > 0 && pathTarget.split("/").length > 0 && pathSourceArray.length == pathTargetArray.length){
			
				for(int i =0 ;i<pathSourceArray.length ;i++){
					
					if(!pathTargetArray[i].contains("{") && !pathSourceArray[i].equals(pathTargetArray[i])){
						
						flag = false;
						
						break;
					}
				}
			
		}else{
			flag = false;
		}
		
		return flag;
		
	}
	
	public static String getResponse(Object object){
		
		Gson gson = new Gson();
		
		return gson.toJson(object);
	}
}
