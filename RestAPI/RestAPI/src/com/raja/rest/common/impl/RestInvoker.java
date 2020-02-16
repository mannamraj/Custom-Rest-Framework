package com.raja.rest.common.impl;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.raja.rest.common.ControllerDetails;
import com.raja.rest.common.Invoker;
import com.raja.rest.common.Loader;
import com.raja.rest.config.Config;
import com.raja.rest.exception.FramerWorkException;
import com.raja.rest.mapper.EndPointMapper;
import com.raja.rest.mapper.Mapper;
import com.raja.rest.util.RestUtil;

public class RestInvoker  implements Invoker<Config,Mapper<?, ?>>{

	public static final String SUCCESS = "success";
	
	private Mapper<?, ?> endPointMapper = null;
	
	Map<String,List<ControllerDetails>> mapper = null ;
	
	private Loader<Class<?>,String> loader = null;

	private Config config = null;
	
	@Override
	public void invoke(Config config,HttpServletRequest request,HttpServletResponse response) {
		
		try{
					
			 ControllerDetails controllerDetails = getControllerDetails(mapper,request.getPathInfo());
	
	         Class<?> clazz = controllerDetails.getClazz();
	
	         Method method = controllerDetails.getMethod();
	         
	         Object[] methodParameters = RestUtil.getArgumetns(request, controllerDetails);
	         
	         Object object = null;
	         
	         if(methodParameters != null && methodParameters.length > 0){
	         	
	        	 object = method.invoke(clazz.newInstance(),methodParameters);
	         	
	         }else{
	
	        	 object = method.invoke(clazz.newInstance());
	         }
	         
	         response.setContentType(controllerDetails.getContentType());
	         
	         PrintWriter out = response.getWriter();
	         
	         out.print(getResponse(method.getReturnType(),object));
	         
	         out.flush();
	         
		}catch (Exception e) {
			throw new FramerWorkException(e, "Invoker Exception");
		}
		
	}
	
	private String getResponse(Type returnType,Object object) {
		
		return Void.TYPE.equals(returnType) ? SUCCESS : RestUtil.getResponse(object);
	}

	private ControllerDetails getControllerDetails(Map<String, List<ControllerDetails>> controllerDetailsMap, String path) {
		
		ControllerDetails controllerDetailsOne = null;
		
		ControllerDetails controllerDetailsTwo = null;
		
		String pathArr[] = path.split("/");
		
		List<ControllerDetails> controllerDetailsList = controllerDetailsMap.get(pathArr[1]);
		
		
		if(RestUtil.isNotEmpty(controllerDetailsList)){
			
			
			for(ControllerDetails controllerDetails : controllerDetailsList){
				
				if(path.equals(controllerDetails.getServicePath())){
					
					controllerDetailsOne = controllerDetails;
					
				}
				
				if(RestUtil.compare(path,controllerDetails.getServicePath())){
					
					controllerDetailsTwo = controllerDetails;
				}
				
			}
		}
		
		return controllerDetailsOne != null ? controllerDetailsOne : controllerDetailsTwo;
	}

	@Override
	public RestInvoker setMapper(Mapper<?, ?> endPointMapper) {
		
		this.endPointMapper = endPointMapper;
		
		return this;
	}

	public Map<String, List<ControllerDetails>> getMapper() {
		return mapper;
	}

	public void map(Map<String, List<ControllerDetails>> mapper) {
		this.mapper = mapper;
	}

	public EndPointMapper getEndPointMapper() {
		return (EndPointMapper) endPointMapper;
	}

	public void setEndPointMapper(Mapper<?, ?> endPointMapper) {
		this.endPointMapper = endPointMapper;
	}

	public Loader<Class<?>, String> getLoader() {
		return loader;
	}

	public RestInvoker setLoader(Loader<Class<?>, String> loader) {
		this.loader = loader;
		return this;
	}

	public Config getConfig() {
		return config;
	}

	public RestInvoker setConfig(Config config) {
		this.config = config;
		return this;
	}

	public RestInvoker build() {
		
		this.mapper = this.getEndPointMapper().map(this.getLoader().load(this.config.getPack()));
		
		return this;
	}
	
	
	
	
	
	

}
