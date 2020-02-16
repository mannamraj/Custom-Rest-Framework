package com.raja.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.raja.rest.common.Invoker;
import com.raja.rest.config.Config;
import com.raja.rest.core.BaseController;
import com.raja.rest.mapper.Mapper;
/**
 * 
 * FrontController
 *  
 * @author albert.mannam
 *
 */
public class FrontController extends BaseController{
	
	private static final long serialVersionUID = 1L;
	
	private Invoker<Config,Mapper<?, ?>> invoker;
	
	private Config config;
	
	/**
	 * This method handles the all Http/Https requests. 
	 */
	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response) {
		
		this.invoker.invoke(config, request, response);
		
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	public void setInvoker(Invoker<Config,Mapper<?, ?>> invoker) {
		this.invoker = invoker;
	}
	
	@Override
	public Config getConfig() {
		return config;
	}
	
}