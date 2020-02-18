package com.raja.rest.core;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.raja.rest.annotations.RestService;
import com.raja.rest.common.Invoker;
import com.raja.rest.common.impl.ClassLoader;
import com.raja.rest.common.impl.RestInvoker;
import com.raja.rest.config.Config;
import com.raja.rest.config.RestWebConfiguration;
import com.raja.rest.mapper.EndPointMapper;
import com.raja.rest.mapper.Mapper;

public abstract class BaseController extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	public  abstract void setInvoker(Invoker<Config,Mapper<?, ?>> invoker);
	
	public abstract void handleRequest(HttpServletRequest arg0, HttpServletResponse arg1);
	
	public abstract void setConfig(Config config);
	
	public abstract Config getConfig();
	
	public static final String SCANNER_PARAM = "package";
	
	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		
		super.init(servletConfig);
		
		setConfig(new RestWebConfiguration().basePack(servletConfig.getInitParameter(SCANNER_PARAM)).loadConfiguration());

		setInvoker(new RestInvoker()
				.setMapper(EndPointMapper.build())
				.setConfig(getConfig())
				.setLoader(new ClassLoader()
				.setAnnotationClass(RestService.class)).build());
		
	}

	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		handleRequest(arg0, arg1);
	}

	
	
}
